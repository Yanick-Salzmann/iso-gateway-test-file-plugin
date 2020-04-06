package ch.yanick.experiments.parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class _IsoGatewayTestLexer
%implements FlexLexer
%unicode

%function advance
%type IElementType

CRLF=\R
METADATA_TOKEN=[^#]
META_KEY_CHAR=[^\r\n:#]
META_VALUE_CHAR=[^\r\n]
SEPARATOR=[:]
DELIMITER=#--

%state DELIMITER_LINE
%state MESSAGE
%state METADATA_KEY
%state METADATA_VALUE

%%

<YYINITIAL> {META_KEY_CHAR}+ { yybegin(METADATA_KEY); return ParsingTokenTypes.META_KEY; }
<METADATA_KEY> {SEPARATOR} { yybegin(METADATA_VALUE); return ParsingTokenTypes.META_KEY; }
<METADATA_VALUE> {META_VALUE_CHAR}+ { yybegin(METADATA_VALUE); return ParsingTokenTypes.META_VALUE; }
<METADATA_VALUE> {CRLF} { yybegin(YYINITIAL); return ParsingTokenTypes.WHITESPACE; }
<DELIMITER_LINE> {CRLF} { yybegin(MESSAGE); return ParsingTokenTypes.WHITESPACE; }
<DELIMITER_LINE> {META_VALUE_CHAR}+ { return ParsingTokenTypes.DELIMITER_LINE; }
<MESSAGE> {DELIMITER} { yybegin(DELIMITER_LINE); return ParsingTokenTypes.DELIMITER_LINE; }
<MESSAGE> . { return ParsingTokenTypes.MESSAGE; }
{CRLF} { return ParsingTokenTypes.WHITESPACE; }
{DELIMITER} { yybegin(DELIMITER_LINE); return ParsingTokenTypes.DELIMITER_LINE; }


[^] { return ParsingTokenTypes.BAD_CHARACTER; }