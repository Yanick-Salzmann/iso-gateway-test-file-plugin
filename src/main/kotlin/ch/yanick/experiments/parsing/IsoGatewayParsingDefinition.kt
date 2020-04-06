package ch.yanick.experiments.parsing

import ch.yanick.experiments.IsoGatewayTestFileLanguage
import ch.yanick.experiments.parsing.psi.*
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.ILightStubFileElementType
import com.intellij.psi.tree.TokenSet

object IsoGwElementTypes {
    val FILE = object : ILightStubFileElementType<PsiFileStub<IsoGwTestFile>>(IsoGatewayTestFileLanguage) {

    }
}

class IsoGatewayParsingDefinition : ParserDefinition {
    override fun createParser(project: Project?): PsiParser = IsoGatewayPsiParser()

    override fun createFile(viewProvider: FileViewProvider) = IsoGwTestFile(viewProvider)

    override fun getStringLiteralElements(): TokenSet = TokenSet.create(
        ParsingTokenTypes.META_KEY, ParsingTokenTypes.META_VALUE, ParsingTokenTypes.MESSAGE
    )

    override fun getFileNodeType() = IsoGwElementTypes.FILE

    override fun createLexer(project: Project?) = IsoGatewayLexerAdatper()

    override fun createElement(node: ASTNode?): PsiElement {
        node ?: throw AssertionError("Cannot create PsiElement from empty node")
        return when(node.elementType) {
            ParsingTokenTypes.META_KEY -> MetaKeyElement(node)
            is MessageElementType -> MessageElement(node)
            is MetadataElementType -> MetadataElement(node)
            is IsoGatewayRootType -> RootElement(node)
            else -> throw AssertionError("PSI element not supported")
        }
    }

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY

}