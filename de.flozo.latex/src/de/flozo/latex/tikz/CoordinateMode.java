package de.flozo.latex.tikz;

public enum CoordinateMode {

    // TikZ coordinates can be absolute (1, 2), relative to the origin +(1, 2),
    // or relative to the origin while setting a new origin ++(1, 2)

    ABSOLUTE(""),
    RELATIVE("+"),
    RELATIVE_SET_ORIGIN("++");


    private final String coordinateMode;

    CoordinateMode(String coordinateMode) {
        this.coordinateMode = coordinateMode;
    }

    public String getString() {
        return coordinateMode;
    }

    @Override
    public String toString() {
        return "CoordinateMode{" +
                "coordinateMode='" + coordinateMode + '\'' +
                '}';
    }
}
