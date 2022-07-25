package de.flozo.latex.core;

public enum Delimiter {

    NONE(""),
    SPACE(" "),
    SEMICOLON(";"),
    COMMA(","),
    DOUBLE_BACKSLASH("\\\\");


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