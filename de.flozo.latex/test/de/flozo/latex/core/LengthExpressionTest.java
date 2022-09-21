package de.flozo.latex.core;

import de.flozo.common.dto.appearance.Length;
import de.flozo.common.dto.appearance.LengthUnit;
import de.flozo.common.dto.appearance.LineWidth;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthExpressionTest {

    double numericalValue = 1.23;
    LengthUnit lengthUnit = new LengthUnit(0, "myUnit", "mu");
    Length length = new Length(0, "myLength", numericalValue, lengthUnit);

    @Test
    void inDefaultUnit() {
        LengthExpression lengthExpression = LengthExpression.inDefaultUnit(numericalValue);
        assertEquals("1.23", lengthExpression.getFormatted());
    }

    @Test
    void inCentimeters() {
        LengthExpression lengthExpression = LengthExpression.inCentimeters(numericalValue);
        assertEquals("1.23cm", lengthExpression.getFormatted());
    }

    @Test
    void fromLength() {
        LengthExpression lengthExpression = LengthExpression.fromLength(length);
        assertEquals("1.23mu", lengthExpression.getFormatted());
    }

    @Test
    void fromLineWidth() {
        LineWidth lineWidth = new LineWidth(0, "myLineWidth", numericalValue, lengthUnit);
        LengthExpression lengthExpression = LengthExpression.fromLineWidth(lineWidth);
        assertEquals("1.23mu", lengthExpression.getFormatted());
    }

    @Test
    void getNumericalValue() {
        LengthExpression lengthExpression = LengthExpression.fromLength(length);
        assertEquals(1.23, lengthExpression.getNumericalValue());
    }

    @Test
    void getUnit() {
        LengthExpression lengthExpression = LengthExpression.fromLength(length);
        assertEquals(lengthUnit, lengthExpression.getUnit());
    }

//    @Test
//    void getFormatted() {
//    }
}
