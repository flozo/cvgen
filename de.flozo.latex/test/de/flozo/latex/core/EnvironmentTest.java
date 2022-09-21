package de.flozo.latex.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    String line1 = "This is the first line of the environment body.";
    String line2 = "This is the second line of the environment body.";
    Environment environment = new Environment.Builder(EnvironmentName.SCOPE)
            .body(line1, line2)
            .optionalArguments("Option1", "Option2", "Option3")
            .requiredArguments("Argument1", "Argument2", "Argument3")
            .build();

    @Test
    void getBlock() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\begin{scope}{Argument1, Argument2, Argument3} [");
        expectedLines.add("\tOption1,");
        expectedLines.add("\tOption2,");
        expectedLines.add("\tOption3");
        expectedLines.add("\t] ");      // Unwanted trailing space!
        expectedLines.add("\t" + line1);
        expectedLines.add("\t" + line2);
        expectedLines.add("\\end{scope}");
        assertLinesMatch(expectedLines, environment.getBlock());
    }

    @Test
    void getInline() {
        assertEquals("\\begin{scope}{Argument1, Argument2, Argument3}[Option1, Option2, Option3]" + line1 + " " + line2 + "\\end{scope}", environment.getInline());
    }
}
