package org.unito.lft.plang;

/**
 * Defines constants representing different token types for the lexer.
 * These constants are used to identify the type of tokens encountered during lexical analysis.
 * Each constant corresponds to a specific token, such as keywords or operators.
 */
public class Tag {
    // PURPOSE: Private constructor, as the class is static.
    private Tag() { assert false; }

    /** Represents the end-of-file token. */
    public static final int EOF = -1;

    /** Represents a numeric literal token. */
    public static final int NUM = 256;

    /** Represents an identifier token. */
    public static final int ID = 257;

    /** Represents a relational operator token. */
    public static final int REL_OP = 258;

    /** Represents an assignment operator token. */
    public static final int ASSIGN = 259;

    /** Represents the 'to' keyword token. */
    public static final int TO = 260;

    /** Represents the 'if' keyword token. */
    public static final int IF = 261;

    /** Represents the 'else' keyword token. */
    public static final int ELSE = 262;

    /** Represents the 'do' keyword token. */
    public static final int DO = 263;

    /** Represents the 'for' keyword token. */
    public static final int FOR = 264;

    /** Represents the 'begin' keyword token. */
    public static final int BEGIN = 265;

    /** Represents the 'end' keyword token. */
    public static final int END = 266;

    /** Represents the 'print' keyword token. */
    public static final int PRINT = 267;

    /** Represents the 'read' keyword token. */
    public static final int READ = 268;

    /** Represents the 'init' keyword token. */
    public static final int INIT = 269;

    /** Represents the 'or' keyword token. */
    public static final int OR = 270;

    /** Represents the 'and' keyword token. */
    public static final int AND = 271;
}
