package de.flozo.common;

public class Length {

    private double numericalValue;
    private LengthUnit unit;


    public Length(double numericalValue, LengthUnit unit) {
        this.numericalValue = numericalValue;
        this.unit = unit;
    }

    public double getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(double numericalValue) {
        this.numericalValue = numericalValue;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    public void setUnit(LengthUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Length{" +
                "numericalValue=" + numericalValue +
                ", unit=" + unit +
                '}';
    }
}
