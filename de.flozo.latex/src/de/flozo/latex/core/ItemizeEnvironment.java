package de.flozo.latex.core;

import java.util.List;

public class ItemizeEnvironment {

    List<String> options;
    List<String> itemList;

    public ItemizeEnvironment(List<String> options, List<String> itemList) {
        this.options = options;
        this.itemList = itemList;
    }

    public Environment getEnvironment() {
        return new Environment.Builder(EnvironmentName.ITEMIZE)
                .optionalArguments(options)
                .body(itemList)
                .build();
    }

    public List<String> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "ItemizeEnvironment{" +
                "options=" + options +
                ", itemList=" + itemList +
                '}';
    }
}
