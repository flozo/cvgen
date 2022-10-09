package de.flozo.latex.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsepackageCommandTest {

    Command command = new GenericCommand.Builder("usepackage")
            .body("body_line_1", "body_line_2")
            .optionList("option1", "option2", "option3")
            .trailingOpeningBracket(true)
            .interBracketSpace(true)
            .skipLastDelimiterOptions(true)
            .skipLastDelimiterBody(true)
            .indentOptions(true)
            .indentBody(true)
            .inlineSpacingOptions(true)
            .inlineSpacingBody(true)
            .optionDelimiter(Delimiter.COMMA)
            .bodyDelimiter(Delimiter.NONE)
            .build();

    @Test
    void getInlineOptions() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\usepackage[option1, option2, option3] {");
        expectedLines.add("\tbody_line_1");
        expectedLines.add("\tbody_line_2");
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, command.getInlineOptions());
    }

    @Test
    void getBlock() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\usepackage [");
        expectedLines.add("\toption1,");
        expectedLines.add("\toption2,");
        expectedLines.add("\toption3");
        expectedLines.add("\t] {");
        expectedLines.add("\tbody_line_1");
        expectedLines.add("\tbody_line_2");
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, command.getBlock());
    }

    @Test
    void getInline() {
        assertEquals("\\usepackage[option1, option2, option3]{body_line_1 body_line_2}", command.getInline());
    }
}
