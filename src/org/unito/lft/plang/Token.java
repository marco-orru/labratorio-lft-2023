package org.unito.lft.plang;

import static java.lang.StringTemplate.STR;

/**
 * Represents a lexical token.
 * Each token has an associated tag, which is an integer identifier.
 */
public class Token {
    private final int tag;

    /**
     * Constructs a new {@link Token} with the specified tag.
     *
     * @param tag The integer identifier for the token.
     */
    public Token(int tag) {
        this.tag = tag;
    }

    /**
     * Gets the tag associated with this token.
     *
     * @return The integer identifier for this token.
     */
    public int getTag() {
        return this.tag;
    }

    /**
     * Returns a string representation of the token.
     *
     * @return A string in the format "⟨{tag}⟩".
     */
    @Override
    public String toString() {
        return STR."⟨\{tag}⟩";
    }

    /** Represents the NOT token. */
    public static final Token NOT = new Token('!');

    /** Represents the LEFT_PARENTHESIS token. */
    public static final Token LEFT_PARENTHESIS = new Token('(');

    /** Represents the RIGHT_PARENTHESIS token. */
    public static final Token RIGHT_PARENTHESIS = new Token(')');

    /** Represents the LEFT_SQUARE_BRACKET token. */
    public static final Token LEFT_SQUARE_BRACKET = new Token('[');

    /** Represents the RIGHT_SQUARE_BRACKET token. */
    public static final Token RIGHT_SQUARE_BRACKET = new Token(']');

    /** Represents the LEFT_CURLY_BRACE token. */
    public static final Token LEFT_CURLY_BRACE = new Token('{');

    /** Represents the RIGHT_CURLY_BRACE token. */
    public static final Token RIGHT_CURLY_BRACE = new Token('}');

    /** Represents the PLUS token. */
    public static final Token PLUS = new Token('+');

    /** Represents the MINUS token. */
    public static final Token MINUS = new Token('-');

    /** Represents the MULTIPLICATION token. */
    public static final Token MULTIPLICATION = new Token('*');

    /** Represents the DIVISION token. */
    public static final Token DIVISION = new Token('/');

    /** Represents the SEMICOLON token. */
    public static final Token SEMICOLON = new Token(';');

    /** Represents the COMMA token. */
    public static final Token COMMA = new Token(',');
}
