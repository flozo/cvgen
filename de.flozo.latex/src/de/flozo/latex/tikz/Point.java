package de.flozo.latex.tikz;


import de.flozo.latex.core.Bracket;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.LengthExpression;


public class Point {

    // constants
    public static final Bracket BRACKETS = Bracket.PARENTHESIS;
    public static final Delimiter VALUE_SEPARATOR = Delimiter.COMMA;
    public static final CoordinateMode DEFAULT_COORDINATE_MODE = CoordinateMode.ABSOLUTE;


    // required
    private final LengthExpression xLength;
    private final LengthExpression yLength;

    // optional
    private final CoordinateMode coordinateMode;


    public Point(LengthExpression xLength, LengthExpression yLength, CoordinateMode coordinateMode) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.coordinateMode = coordinateMode;
    }


    public static Point fromLengthsInMode(LengthExpression xLength, LengthExpression yLength, CoordinateMode coordinateMode) {
        return new Point(xLength, yLength, coordinateMode);
    }

    public static Point fromLengths(LengthExpression xLength, LengthExpression yLength) {
        return fromLengthsInMode(xLength, yLength, DEFAULT_COORDINATE_MODE);
    }

    public static Point fromNumbersInMode(double x, double y, CoordinateMode coordinateMode) {
        return fromLengthsInMode(LengthExpression.inDefaultUnit(x), LengthExpression.inDefaultUnit(y), coordinateMode);
    }

    public static Point fromNumbers(double x, double y) {
        return fromNumbersInMode(x, y, DEFAULT_COORDINATE_MODE);
    }




    public String getStatement() {
        return coordinateMode.getString() + BRACKETS.getLeftBracket() +
                xLength.getFormatted() +
                VALUE_SEPARATOR.getString() + " " +
                yLength.getFormatted() +
                BRACKETS.getRightBracket();
    }

    public double getXValue() {
        return xLength.getNumericalValue();
    }

    public double getYValue() {
        return yLength.getNumericalValue();
    }

    @Override
    public String toString() {
        return "Point{" +
                "xLength=" + xLength +
                ", yLength=" + yLength +
                ", coordinateMode=" + coordinateMode +
                '}';
    }
}
