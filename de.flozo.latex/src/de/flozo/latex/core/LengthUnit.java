package de.flozo.latex.core;

public enum LengthUnit {

    DEFAULT(""),
    POINT("pt"),
    MILLIMETER("mm"),
    CENTIMETER("cm"),
    INCH("in"),
    EX("ex"),   // font dependent; roughly the height of a lowercase x in the current font
    EM("em"),   // font dependent; roughly the width of an uppercase M in the current font
    SP("sp");   // special point; 1pt = 65536sp


    private final String lengthUnit;

    LengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public String getString() {
        return lengthUnit;
    }

    @Override
    public String toString() {
        return "LengthUnit{" +
                "lengthUnit='" + lengthUnit + '\'' +
                '}';
    }
}
