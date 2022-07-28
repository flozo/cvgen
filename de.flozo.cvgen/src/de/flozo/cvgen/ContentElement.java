package de.flozo.cvgen;

import java.util.ArrayList;
import java.util.List;

public class ContentElement {

    public static final String DEFAULT_DELIMITER = "";
    private final List<String> components;
    private final String delimiter;

    private ContentElement(Builder builder) {
        this.components = builder.components;
        this.delimiter = builder.delimiter;
    }

    private String joinComponentsWithDelimiter(String delimiter) {
        return String.join(delimiter, components);
    }

    public String inline() {
        return joinComponentsWithDelimiter(delimiter);
    }

    public String multiline() {
        return joinComponentsWithDelimiter("\\\\");      // join components with LaTeX line breaks
    }


    public static class Builder {

        // required
        private final List<String> components;

        // optional
        private String delimiter = DEFAULT_DELIMITER;

        public Builder(List<String> components) {
            this.components = components;
        }

        public Builder(String... components) {
            this(new ArrayList<>(List.of(components)));
        }


        public Builder() {
            this.components = new ArrayList<>();
        }

        public Builder addComponent(String component) {
            this.components.add(component);
            return this;
        }

        public Builder addComponents(List<String> componentList) {
            this.components.addAll(componentList);
            return this;
        }

        public Builder addComponents(String... components) {
            return addComponents(new ArrayList<>(List.of(components)));
        }

        public Builder inlineDelimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }


        public ContentElement build() {
            return new ContentElement(this);
        }

    }



}
