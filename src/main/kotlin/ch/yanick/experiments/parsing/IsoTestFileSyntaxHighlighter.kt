package ch.yanick.experiments.parsing

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType

class IsoTestFileSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        private val META_KEY_ATTRIBUTES = createTextAttributesKey("META_KEY", DefaultLanguageHighlighterColors.CONSTANT)
        private val META_VALUE_ATTRIBUTES =
            createTextAttributesKey("META_VALUE", DefaultLanguageHighlighterColors.METADATA)
        private val DELIMITER_ATTRIBUTES =
            createTextAttributesKey("DELIMITER_LINE", DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val MESSAGE_ATTRIBUTES = createTextAttributesKey("MESSAGE", DefaultLanguageHighlighterColors.STRING)
        private val BAD_CHAR_ATTRIBUTES = createTextAttributesKey("BAD_CHARACTERS", HighlighterColors.BAD_CHARACTER)
    }

    override fun getHighlightingLexer(): Lexer {
        return IsoGatewayLexerAdatper()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            ParsingTokenTypes.META_KEY -> arrayOf(META_KEY_ATTRIBUTES)
            ParsingTokenTypes.META_VALUE -> arrayOf(META_VALUE_ATTRIBUTES)
            ParsingTokenTypes.DELIMITER_LINE -> arrayOf(DELIMITER_ATTRIBUTES)
            ParsingTokenTypes.MESSAGE -> arrayOf(MESSAGE_ATTRIBUTES)
            ParsingTokenTypes.BAD_CHARACTER -> arrayOf(BAD_CHAR_ATTRIBUTES)
            else -> emptyArray()
        }
    }
}

class IsoTestFileSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return IsoTestFileSyntaxHighlighter()
    }
}