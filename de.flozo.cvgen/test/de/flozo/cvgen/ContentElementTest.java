package de.flozo.cvgen;

import de.flozo.latex.core.Delimiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentElementTest {

    ContentElement.Builder contentElement = new ContentElement.Builder()
            .addComponent("One")
            .addComponent("Two")
            .addComponent("Three");

    @Test
    void getInline() {
        assertEquals("OneTwoThree", contentElement.build().getInline());
    }

    @Test
    void getInlineSpaces() {
        assertEquals("One Two Three", contentElement
                .inlineDelimiter(Delimiter.SPACE)
                .build().getInline());
    }

    @Test
    void getInlineSpacesFinalComma() {
        assertEquals("One Two Three,", contentElement
                .inlineDelimiter(Delimiter.SPACE)
                .finalDelimiter(Delimiter.COMMA)
                .build().getInline());
    }

}
