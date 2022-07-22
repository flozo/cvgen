package de.flozo.cvgen;

import java.util.ArrayList;
import java.util.List;

public class ContentElement {

    private final List<String> partsList;

    public ContentElement(String... parts) {
        this.partsList = new ArrayList<>(List.of(parts));
    }

    public String inline(String delimiter) {
        return String.join(delimiter, partsList);
    }

    public String multiline() {
        return inline("\\\\");      // join parts with LaTeX line breaks
    }


    @Override
    public String toString() {
        return "ContentElement{" +
                "partsList=" + partsList +
                '}';
    }
}
