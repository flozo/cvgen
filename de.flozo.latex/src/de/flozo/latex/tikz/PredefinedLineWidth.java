package de.flozo.latex.tikz;

public enum PredefinedLineWidth {

    // TikZ graphic parameter: line width
    // (used without parameter name; version with parameter name takes numerical values only)
    // permissible types are ultra thin, very thin, thin, semithick, thick, very thick, and ultra thick

    ULTRA_THIN("ultra thin"),
    VERY_THIN("very thin"),
    THIN("thin"),
    SEMITHICK("semithick"),
    THICK("thick"),
    VERY_THICK("very thick"),
    ULTRA_THICK("ultra thick");


    private final String predefinedLineWidth;

    PredefinedLineWidth(String predefinedLineWidth) {
        this.predefinedLineWidth = predefinedLineWidth;
    }

    public String getString() {
        return predefinedLineWidth;
    }

    @Override
    public String toString() {
        return "PredefinedLineWidth{" +
                "predefinedLineWidth='" + predefinedLineWidth + '\'' +
                '}';
    }
}
