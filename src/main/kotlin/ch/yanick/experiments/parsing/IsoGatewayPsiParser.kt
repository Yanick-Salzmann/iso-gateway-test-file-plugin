package ch.yanick.experiments.parsing

import ch.yanick.experiments.parsing.psi.IsoGatewayRootType
import ch.yanick.experiments.parsing.psi.MessageElementType
import ch.yanick.experiments.parsing.psi.MetadataElementType
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

class IsoGatewayPsiParser : PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val rootMarker = builder.mark()

        var isInFirstMessage = false
        var hasFirstMessage = false
        var isInSecondMessage = false
        var hasSecondMessage = false

        var firstMessageMarker: PsiBuilder.Marker? = null
        var secondMessageMarker: PsiBuilder.Marker? = null

        var sourceUri = ""
        var destUri = ""

        var curElementString: StringBuilder
        var wasLastDelimiter = false

        while(!builder.eof()) {
            var elemType = builder.tokenType
            if(elemType == ParsingTokenTypes.META_KEY) {
                wasLastDelimiter = false
                curElementString = StringBuilder()
                curElementString.append(builder.tokenText)
                val kv = builder.mark()
                builder.advanceLexer()
                elemType = builder.tokenType

                while(elemType == ParsingTokenTypes.META_KEY || elemType == ParsingTokenTypes.WHITESPACE) {
                    curElementString.append(builder.tokenText)
                    builder.advanceLexer()
                    elemType = builder.tokenType
                }

                curElementString.append(builder.tokenText)
                if(elemType != ParsingTokenTypes.META_VALUE) {
                    throw AssertionError("Metadata key must be followed by metadata value")
                }

                builder.advanceLexer()
                while(elemType == ParsingTokenTypes.META_VALUE || elemType == ParsingTokenTypes.WHITESPACE) {
                    curElementString.append(builder.tokenText)
                    builder.advanceLexer()
                    elemType = builder.tokenType
                }

                val metaContent = curElementString.toString()
                if(metaContent.startsWith("source uri", true)) {
                    sourceUri = metaContent.substring(metaContent.indexOf(':') + 1).trim()
                } else if(metaContent.startsWith("destination uri", true) || metaContent.startsWith("target uri", true)) {
                    destUri = metaContent.substring(metaContent.indexOf(':') + 1).trim()
                }

                kv.done(MetadataElementType())
                continue
            } else if(elemType == ParsingTokenTypes.DELIMITER_LINE) {
                if(wasLastDelimiter) {
                    builder.advanceLexer()
                    continue
                }

                wasLastDelimiter = true
                if(!isInFirstMessage) {
                    while(elemType == ParsingTokenTypes.DELIMITER_LINE || elemType == ParsingTokenTypes.WHITESPACE) {
                        builder.advanceLexer()
                        elemType = builder.tokenType
                    }

                    firstMessageMarker = builder.mark()
                    isInFirstMessage = true
                } else if(isInFirstMessage && !hasFirstMessage && !isInSecondMessage && !hasSecondMessage) {
                    firstMessageMarker?.done(MessageElementType(sourceUri)) ?: throw AssertionError("Internal error, message marker cannot be null")
                    isInFirstMessage = false
                    hasFirstMessage = true
                    isInSecondMessage = true
                    while(elemType == ParsingTokenTypes.DELIMITER_LINE || elemType == ParsingTokenTypes.WHITESPACE) {
                        builder.advanceLexer()
                        elemType = builder.tokenType
                    }

                    secondMessageMarker = builder.mark()
                } else if(isInSecondMessage && !hasSecondMessage){
                    builder.advanceLexer()
                    hasSecondMessage = true
                    isInSecondMessage = false
                }
            } else {
                wasLastDelimiter = false
                builder.advanceLexer()
            }
        }

        if(isInFirstMessage && !hasFirstMessage) {
            firstMessageMarker?.done(MessageElementType(sourceUri)) ?: throw AssertionError("Internal error, message marker cannot be null")
        }

        if(isInSecondMessage && !hasSecondMessage) {
            secondMessageMarker?.done(MessageElementType(destUri)) ?: throw AssertionError("Internal error, message marker cannot be null")
        }

        rootMarker.done(IsoGatewayRootType())
        return builder.treeBuilt
    }

}