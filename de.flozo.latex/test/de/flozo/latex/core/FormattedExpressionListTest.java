package de.flozo.latex.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;


class FormattedExpressionListTest {

    ExpressionList defaultSettings = new FormattedExpressionList.Builder()
            .append("Expression1")
            .append("Expression2")
            .append("Expression3")
            .build();
    ExpressionList explicitDefaultSettings = new FormattedExpressionList.Builder()
            .append("Expression1")
            .append("Expression2")
            .append("Expression3")
            .brackets(Bracket.NONE)
            .delimiter(Delimiter.NONE)
            .indentBlock(true)
            .inlineSpacing(true)
            .openingBracketMode(BracketMode.SEPARATE_LINE)
            .closingBracketMode(BracketMode.SEPARATE_LINE)
            .skipLastDelimiter(true)
            .indentBlock(false)
            .inlineSpacing(true)
            .build();
    ExpressionList customSettings = new FormattedExpressionList.Builder()
            .append("Expression1")
            .append("Expression2")
            .append("Expression3")
            .brackets(Bracket.CURLY_BRACES)
            .delimiter(Delimiter.DOUBLE_BACKSLASH)
            .indentBlock(false)
            .inlineSpacing(false)
            .openingBracketMode(BracketMode.SKIP)
            .closingBracketMode(BracketMode.AFFIXED)
            .skipLastDelimiter(false)
            .indentBlock(true)
            .inlineSpacing(false)
            .build();

    @Test
    void getBlock_defaults() {
        List<String> lines = new ArrayList<>();
        lines.add("Expression1");
        lines.add("Expression2");
        lines.add("Expression3");
        assertLinesMatch(lines, defaultSettings.getBlock());
    }

    @Test
    void getBlock_explicitDefaults() {
        List<String> lines = new ArrayList<>();
        lines.add("Expression1");
        lines.add("Expression2");
        lines.add("Expression3");
        assertLinesMatch(lines, explicitDefaultSettings.getBlock());
    }

    @Test
    void getBlock_custom() {
        List<String> lines = new ArrayList<>();
        lines.add("\tExpression1\\\\");
        lines.add("\tExpression2\\\\");
        lines.add("\tExpression3\\\\");
        lines.add("\t}");                   // AFFIXED doesn't work!
        assertLinesMatch(lines, customSettings.getBlock());
    }

    @Test
    void getInline_defaults() {
        assertEquals("Expression1 Expression2 Expression3", defaultSettings.getInline());
    }

    @Test
    void getInline_explicitDefaults() {
        assertEquals("Expression1 Expression2 Expression3", explicitDefaultSettings.getInline());
    }

    @Test
    void getInline_custom() {
        assertEquals("{Expression1\\\\Expression2\\\\Expression3\\\\}", customSettings.getInline());    // SKIP doesn't work!
    }

}
