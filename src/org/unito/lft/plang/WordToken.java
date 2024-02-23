package org.unito.lft.plang;

/**
 * Represents lexical tokens for words (keywords and operators).
 * It extends the {@code Token} class.
 */
public class WordToken extends Token {
    private final String lexeme;

    /**
     * Constructs a new {@link WordToken} with the specified tag and lexeme.
     *
     * @param tag The integer identifier for the token.
     * @param lexeme The actual word or operator represented by the token.
     */
    public WordToken(int tag, String lexeme) {
        super(tag);
        this.lexeme = lexeme;
    }

    /**
     * Gets the lexeme associated with this word token.
     *
     * @return The word or operator represented by this token.
     */
    public String getLexeme() {
        return this.lexeme;
    }

    /**
     * Returns a string representation of this word token.
     *
     * @return A string in the format "⟨{tag}, {lexeme}⟩".
     */
    @Override
    public String toString() {
        return STR."⟨\{getTag()}, \{lexeme}⟩";
    }

    /** Represents the 'assign' keyword token. */
    public static final WordToken ASSIGN_KWD = new WordToken(Tag.ASSIGN, "assign");

    /** Represents the 'to' keyword token. */
    public static final WordToken TO_KWD = new WordToken(Tag.TO, "to");

    /** Represents the 'if' keyword token. */
    public static final WordToken IF_KWD = new WordToken(Tag.IF, "if");

    /** Represents the 'else' keyword token. */
    public static final WordToken ELSE_KWD = new WordToken(Tag.ELSE, "else");

    /** Represents the 'do' keyword token. */
    public static final WordToken DO_KWD = new WordToken(Tag.DO, "do");

    /** Represents the 'for' keyword token. */
    public static final WordToken FOR_KWD = new WordToken(Tag.FOR, "for");

    /** Represents the 'begin' keyword token. */
    public static final WordToken BEGIN_KWD = new WordToken(Tag.BEGIN, "begin");

    /** Represents the 'end' keyword token. */
    public static final WordToken END_KWD = new WordToken(Tag.END, "end");

    /** Represents the 'print' keyword token. */
    public static final WordToken PRINT_KWD = new WordToken(Tag.PRINT, "print");

    /** Represents the 'read' keyword token. */
    public static final WordToken READ_KWD = new WordToken(Tag.READ, "read");

    /** Represents the ':=' operator token. */
    public static final WordToken INIT = new WordToken(Tag.INIT, ":=");

    /** Represents the '||' operator token. */
    public static final WordToken OR = new WordToken(Tag.OR, "||");

    /** Represents the '&&' operator token. */
    public static final WordToken AND = new WordToken(Tag.AND, "&&");

    /** Represents the '<' relational operator token. */
    public static final WordToken LESS_THAN = new WordToken(Tag.REL_OP, "<");

    /** Represents the '>' relational operator token. */
    public static final WordToken GREATER_THAN = new WordToken(Tag.REL_OP, ">");

    /** Represents the '==' relational operator token. */
    public static final WordToken EQUAL_TO = new WordToken(Tag.REL_OP, "==");

    /** Represents the '<=' relational operator token. */
    public static final WordToken LESS_THAN_OR_EQUAL_TO = new WordToken(Tag.REL_OP, "<=");

    /** Represents the '<>' relational operator token. */
    public static final WordToken NOT_EQUAL_TO = new WordToken(Tag.REL_OP, "<>");

    /** Represents the '>=' relational operator token. */
    public static final WordToken GREATER_THAN_OR_EQUAL_TO = new WordToken(Tag.REL_OP, ">=");
}
