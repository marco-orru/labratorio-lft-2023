package org.unito.lft.plang;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents a lexical analyzer for tokenizing input source code.
 * It reads characters from a {@code BufferedReader} and generates tokens based on the recognized patterns.
 */
public final class Lexer {
    private int line;
    private char peek;
    private final BufferedReader reader;

    /**
     * Constructs a new {@link Lexer} with the specified reader.
     *
     * @param reader The {@link BufferedReader} providing the input source code.
     */
    public Lexer(BufferedReader reader) {
        this.reader = reader;
        this.line = 1;
        this.peek = ' ';
    }

    // PURPOSE: Reads the next char into 'peek', setting it to -1 if EOF.
    private void readChar() {
        try {
            peek = (char) reader.read();
        } catch (IOException e) {
            peek = (char) -1;
        }
    }

    // PURPOSE: Resets the peek and returns the token passed as parameter.
    private Token resetPeekAndReturn(Token token) {
        peek = ' ';
        return token;
    }

    // PURPOSE: Tries to read a word token. If successful, returns the word token, otherwise returns null.
    private WordToken tryReadWord() throws IOException {
        if (!Character.isLetter(peek) && peek != '_')
            return null;

        var underscoreSeq = true;
        var word = new StringBuilder();

        do {
            word.append(peek);

            if (underscoreSeq)
                underscoreSeq = peek == '_';

            readChar();
        } while (Character.isLetter(peek) || Character.isDigit(peek) || peek == '_');

        if (underscoreSeq)
            throw new IOException("Identifier name cannot be made of underscores only");

        var lexeme = word.toString();
        switch (lexeme) {
            case "assign" -> {
                return WordToken.ASSIGN_KWD;
            }
            case "to" -> {
                return WordToken.TO_KWD;
            }
            case "if" -> {
                return WordToken.IF_KWD;
            }
            case "else" -> {
                return WordToken.ELSE_KWD;
            }
            case "do" -> {
                return WordToken.DO_KWD;
            }
            case "for" -> {
                return WordToken.FOR_KWD;
            }
            case "begin" -> {
                return WordToken.BEGIN_KWD;
            }
            case "end" -> {
                return WordToken.END_KWD;
            }
            case "print" -> {
                return WordToken.PRINT_KWD;
            }
            case "read" -> {
                return WordToken.READ_KWD;
            }
            default -> {
                return new WordToken(Tag.ID, lexeme);
            }
        }
    }

    // PURPOSE: Tries to read a number token. If successful, returns the number token, otherwise returns null.
    private NumberToken tryReadNumber() {
        if (!Character.isDigit(peek))
            return null;

        if (peek == '0')
            return (NumberToken) resetPeekAndReturn(new NumberToken(0));

        var number = new StringBuilder();
        do {
            number.append(peek);
            readChar();
        } while (Character.isDigit(peek));
        return new NumberToken(Integer.parseInt(number.toString()));
    }

    // PURPOSE: Resets the peek and returns the token if peek is equal to the specified char, otherwise throws.
    private Token resetPeekAndReturnIfEq(Token token, char ifEq) throws IOException {
        if (peek != ifEq) {
            throw new IOException(STR."Invalid character after '\{ifEq}' at line \{line}");
        }

        return resetPeekAndReturn(token);
    }

    // PURPOSE: Skips an inline comment.
    private void skipInlineComment() {
        do readChar();
        while (peek != (char) -1 && peek != '\n');

        peek = ' ';
    }

    // PURPOSE: Skips a block comment.
    private void skipBlockComment() throws IOException {
        var foundAsterisk = false;
        var inBlockComment = true;

        do {
            readChar();

            if (foundAsterisk && peek == '/')
                inBlockComment = false;
            else if (!foundAsterisk && peek == '*')
                foundAsterisk = true;
            else if (foundAsterisk)
                foundAsterisk = false;

        } while (inBlockComment && peek != (char) -1);

        if (inBlockComment)
            throw new IOException("Block comment never closed");

        peek = ' ';
    }

    /**
     * Retrieves the next token from the input source code.
     *
     * @return The next token recognized by the lexer.
     */
    public Token getNextToken() throws IOException {
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n') line++;
            readChar();
        }

        switch (peek) {
            case (char) -1 -> {
                return new Token(Tag.EOF);
            }
            case '!' -> {
                return resetPeekAndReturn(Token.NOT);
            }
            case '(' -> {
                return resetPeekAndReturn(Token.LEFT_PARENTHESIS);
            }
            case ')' -> {
                return resetPeekAndReturn(Token.RIGHT_PARENTHESIS);
            }
            case '[' -> {
                return resetPeekAndReturn(Token.LEFT_SQUARE_BRACKET);
            }
            case ']' -> {
                return resetPeekAndReturn(Token.RIGHT_SQUARE_BRACKET);
            }
            case '{' -> {
                return resetPeekAndReturn(Token.LEFT_CURLY_BRACE);
            }
            case '}' -> {
                return resetPeekAndReturn(Token.RIGHT_CURLY_BRACE);
            }
            case '+' -> {
                return resetPeekAndReturn(Token.PLUS);
            }
            case '-' -> {
                return resetPeekAndReturn(Token.MINUS);
            }
            case '*' -> {
                return resetPeekAndReturn(Token.MULTIPLICATION);
            }
            case '/' -> {
                readChar();

                switch (peek) {
                    case '/' -> {
                        skipInlineComment();
                        return getNextToken();
                    }
                    case '*' -> {
                        skipBlockComment();
                        return getNextToken();
                    }
                    default -> {
                        return Token.DIVISION;
                    }
                }
            }
            case ';' -> {
                return resetPeekAndReturn(Token.SEMICOLON);
            }
            case ',' -> {
                return resetPeekAndReturn(Token.COMMA);
            }
            case '&' -> {
                readChar();
                return resetPeekAndReturnIfEq(WordToken.AND, '&');
            }
            case '|' -> {
                readChar();
                return resetPeekAndReturnIfEq(WordToken.OR, '|');
            }
            case '=' -> {
                readChar();
                return resetPeekAndReturnIfEq(WordToken.EQUAL_TO, '=');
            }
            case '<' -> {
                readChar();

                if (peek == '=')
                    return resetPeekAndReturn(WordToken.LESS_THAN_OR_EQUAL_TO);
                else if (peek == '>')
                    return resetPeekAndReturn(WordToken.NOT_EQUAL_TO);

                return WordToken.LESS_THAN;
            }
            case '>' -> {
                readChar();

                if (peek == '=')
                    return resetPeekAndReturn(WordToken.GREATER_THAN_OR_EQUAL_TO);

                return WordToken.GREATER_THAN;
            }
            case ':' -> {
                readChar();
                return resetPeekAndReturnIfEq(WordToken.INIT, '=');
            }
            default -> {
                var wordToken = tryReadWord();
                if (wordToken != null)
                    return wordToken;

                var numberToken = tryReadNumber();
                if (numberToken != null)
                    return numberToken;

                throw new IOException(STR."Invalid character '\{peek}' at line \{line}");
            }
        }
    }
}
