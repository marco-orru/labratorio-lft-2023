package org.unito.lft.plang;

/**
 * Represents a numeric literal token.
 * It extends the {@code Token} class and includes the numeric value associated with the token.
 */
public class NumberToken extends Token {
    private final int value;

    /**
     * Constructs a new {@link NumberToken} with the specified numeric value.
     *
     * @param value The numeric value represented by the token.
     */
    public NumberToken(int value) {
        super(Tag.NUM);
        this.value = value;
    }

    /**
     * Gets the numeric value associated with this number token.
     *
     * @return The numeric value represented by this token.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns a string representation of this number token.
     *
     * @return A string in the format "⟨{tag}, {value}⟩".
     */
    @Override
    public String toString() {
        return STR."⟨\{getTag()}, \{value}⟩";
    }
}
