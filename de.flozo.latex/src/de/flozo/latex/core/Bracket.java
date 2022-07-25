package de.flozo.latex.core;

public enum Bracket {

    NONE("", ""),
    PARENTHESIS("(", ")"),
    SQUARE_BRACKETS("[", "]"),
    CURLY_BRACES("{", "}");


    private final String leftBracket;
    private final String rightBracket;

    Bracket(String leftBracket, String rightBracket) {
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
    }

    public String getLeftBracket() {
        return leftBracket;
    }

    public String getRightBracket() {
        return rightBracket;
    }

    @Override
    public String toString() {
        return "Bracket{" +
                "leftBracket='" + leftBracket + '\'' +
                ", rightBracket='" + rightBracket + '\'' +
                '}';
    }
}
