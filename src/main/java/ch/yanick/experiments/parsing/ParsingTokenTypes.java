package ch.yanick.experiments.parsing;

public class ParsingTokenTypes {
    public static final ParsingElementType META_KEY = new ParsingElementType("META_KEY");
    public static final ParsingElementType META_VALUE = new ParsingElementType("META_VALUE");
    public static final ParsingElementType DELIMITER_LINE = new ParsingElementType("DELIMITER_LINE");
    public static final ParsingElementType MESSAGE = new ParsingElementType("MESSAGE");
    public static final ParsingElementType BAD_CHARACTER = new ParsingElementType("BAD_CHARACTER");
    public static final ParsingElementType WHITESPACE = new ParsingElementType("WHITESPACE");
}
