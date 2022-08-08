package de.flozo.latex.core;

public enum Delimiter {

    NONE(""),
    SPACE(" "),
    NON_BREAKING_SPACE("~"),
    SEMICOLON(";"),
    COMMA(","),
    DOUBLE_BACKSLASH("\\\\"),
    EQUALS("=");


    private final String delimiter;

    Delimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getString() {
        return delimiter;
    }

    @Override
    public String toString() {
        return "Delimiter{" +
                "delimiter='" + delimiter + '\'' +
                '}';
    }
}
