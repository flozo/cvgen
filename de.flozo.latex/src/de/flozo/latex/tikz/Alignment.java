package de.flozo.latex.tikz;

public enum Alignment {

    LEFT("left"),
    RIGHT("right"),
    CENTER("center"),
    JUSTIFY("justify"),
    FLUSH_LEFT("flush left"),
    FLUSH_RIGHT("flush right"),
    FLUSH_CENTER("flush center"),
    NONE("none");


    private final String alignment;

    Alignment(String alignment) {
        this.alignment = alignment;
    }

    public String getString() {
        return alignment;
    }

    @Override
    public String toString() {
        return "Alignment{" +
                "alignment='" + alignment + '\'' +
                '}';
    }
}
