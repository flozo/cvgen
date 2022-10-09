package de.flozo.latex.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemizeEnvironmentTest {

    List<String> optionList = List.of("option1", "option2", "option3");
    List<String> itemList = List.of("item1", "item2", "item3");

    ItemizeEnvironment itemizeEnvironment = new ItemizeEnvironment(optionList, itemList);

    @Test
    void getEnvironment() {
        Environment expectedEnvironment = new Environment.Builder(EnvironmentName.ITEMIZE)
                .optionalArguments(optionList)
                .body(itemList)
                .build();
        assertLinesMatch(expectedEnvironment.getBlock(), itemizeEnvironment.getEnvironment().getBlock());
    }

    @Test
    void getItemList() {
        assertLinesMatch(itemList, itemizeEnvironment.getItemList());
    }
}
