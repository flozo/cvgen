package de.flozo.latex.core;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class GenericCommandTest {

    String commandBodyLine1 = "This is the first line of the command body";
    String commandBodyLine2 = "This is the second line of the command body";


    Command default_settings = new GenericCommand.Builder("testcommand1")
            .body(commandBodyLine1, commandBodyLine2)
            .optionList("Option1", "Option2", "Option3")
            .build();
    Command explicit_default_settings = new GenericCommand.Builder("testcommand2")
            .body(commandBodyLine1, commandBodyLine2)
            .optionList("Option1", "Option2", "Option3")
            .optionBrackets(Bracket.SQUARE_BRACKETS)
            .bodyBrackets(Bracket.CURLY_BRACES)
            .optionDelimiter(Delimiter.COMMA)
            .bodyDelimiter(Delimiter.NONE)
            .inlineSpacingBody(true)
            .inlineSpacingOptions(true)
            .interBracketSpace(true)
            .trailingOpeningBracket(true)
            .skipLastDelimiterOptions(true)
            .skipLastDelimiterBody(true)
            .indentOptions(true)
            .indentBody(true)
            .build();
    Command custom_settings = new GenericCommand.Builder("testcommand3")
            .body(commandBodyLine1, commandBodyLine2)
            .optionList("Option1", "Option2", "Option3")
            .optionBrackets(Bracket.PARENTHESIS)
            .bodyBrackets(Bracket.SQUARE_BRACKETS)
            .optionDelimiter(Delimiter.NON_BREAKING_SPACE)
            .bodyDelimiter(Delimiter.SEMICOLON)
            .inlineSpacingBody(false)
            .inlineSpacingOptions(false)
            .interBracketSpace(false)
            .trailingOpeningBracket(false)
            .skipLastDelimiterOptions(false)
            .skipLastDelimiterBody(false)
            .indentOptions(true)
            .indentBody(true)
            .build();


    @org.junit.jupiter.api.Test
    void getBlock_defaults() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand1 [");
        expectedLines.add("\tOption1,");
        expectedLines.add("\tOption2,");
        expectedLines.add("\tOption3");
        expectedLines.add("\t] {");
        expectedLines.add("\t" + commandBodyLine1);
        expectedLines.add("\t" + commandBodyLine2);
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, default_settings.getBlock());
    }

    @org.junit.jupiter.api.Test
    void getBlock_explicit_defaults() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand2 [");
        expectedLines.add("\tOption1,");
        expectedLines.add("\tOption2,");
        expectedLines.add("\tOption3");
        expectedLines.add("\t] {");
        expectedLines.add("\t" + commandBodyLine1);
        expectedLines.add("\t" + commandBodyLine2);
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, explicit_default_settings.getBlock());
    }

    @org.junit.jupiter.api.Test
    void getBlock_custom() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand3");
        expectedLines.add("\t(");
        expectedLines.add("\tOption1~");
        expectedLines.add("\tOption2~");
        expectedLines.add("\tOption3~");
        expectedLines.add("\t)");
        expectedLines.add("\t[");
        expectedLines.add("\t" + commandBodyLine1 + ";");
        expectedLines.add("\t" + commandBodyLine2 + ";");
        expectedLines.add("\t]");
        assertLinesMatch(expectedLines, custom_settings.getBlock());
    }

    @org.junit.jupiter.api.Test
    void getInlineOptions_defaults() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand1[Option1, Option2, Option3] {");
        expectedLines.add("\t" + commandBodyLine1);
        expectedLines.add("\t" + commandBodyLine2);
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, default_settings.getInlineOptions());
    }

    @org.junit.jupiter.api.Test
    void getInlineOptions_explicit_defaults() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand2[Option1, Option2, Option3] {");
        expectedLines.add("\t" + commandBodyLine1);
        expectedLines.add("\t" + commandBodyLine2);
        expectedLines.add("\t}");
        assertLinesMatch(expectedLines, explicit_default_settings.getInlineOptions());
    }

    @org.junit.jupiter.api.Test
    void getInlineOptions_custom() {
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("\\testcommand3(Option1~Option2~Option3~)");
        expectedLines.add("\t[");
        expectedLines.add("\t" + commandBodyLine1 + ";");
        expectedLines.add("\t" + commandBodyLine2 + ";");
        expectedLines.add("\t]");
        assertLinesMatch(expectedLines, custom_settings.getInlineOptions());
    }


    @org.junit.jupiter.api.Test
    void getInline_defaults() {
        assertEquals("\\testcommand1[Option1, Option2, Option3]{" + commandBodyLine1 + " " + commandBodyLine2 + "}", default_settings.getInline());
    }

    @org.junit.jupiter.api.Test
    void getInline_explicit_defaults() {
        assertEquals("\\testcommand2[Option1, Option2, Option3]{" + commandBodyLine1 + " " + commandBodyLine2 + "}", explicit_default_settings.getInline());
    }

    @org.junit.jupiter.api.Test
    void getInline_custom() {
        assertEquals("\\testcommand3(Option1~Option2~Option3~)[" + commandBodyLine1 + ";" + commandBodyLine2 + ";]", custom_settings.getInline());
    }


}
