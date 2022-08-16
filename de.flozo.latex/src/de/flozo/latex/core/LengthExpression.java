package de.flozo.latex.core;

import de.flozo.common.dto.appearance.Length;
import de.flozo.common.dto.appearance.LengthUnit;
import de.flozo.common.dto.appearance.LineWidth;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LengthExpression {

    public static final LengthUnit DEFAULT_LENGTH_UNIT = new LengthUnit(1, "default", "");
    public static final LengthUnit CENTIMETER_LENGTH_UNIT = new LengthUnit(4, "centimeter", "cm");

    private final double numericalValue;
    private final LengthUnit unit;


    private LengthExpression(double numericalValue) {
        this(numericalValue, DEFAULT_LENGTH_UNIT);
    }

    private LengthExpression(double numericalValue, LengthUnit unit) {
        this.numericalValue = numericalValue;
        this.unit = unit;
    }

    public static LengthExpression inDefaultUnit(double numericalValue) {
        return new LengthExpression(numericalValue);
    }

    public static LengthExpression inCentimeters(double numericalValue) {
        return new LengthExpression(numericalValue, CENTIMETER_LENGTH_UNIT);
    }


    public static LengthExpression fromLength(Length length) {
        return new LengthExpression(length.getValue(), length.getUnit());
    }

    public static LengthExpression fromLineWidth(LineWidth lineWidth) {
        return new LengthExpression(lineWidth.getValue(), lineWidth.getUnit());
    }


    public double getNumericalValue() {
        return numericalValue;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    public String getFormatted() {
        // Avoid trailing zeros; ensure point is used as decimal separator
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        return (df.format(numericalValue) + unit.getValue());
    }

    @Override
    public String toString() {
        return "LengthExpression{" +
                "numericalValue=" + numericalValue +
                ", unit=" + unit +
                '}';
    }
}
