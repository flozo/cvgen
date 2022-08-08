package de.flozo.cvgen;

import de.flozo.latex.core.Bracket;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.GenericCommand;

import java.util.ArrayList;
import java.util.List;

public class ContentElement {

    public static final String DEFAULT_DELIMITER = "";
    public static final boolean DEFAULT_MULTILINE_CONTENT = false;
    public static final boolean DEFAULT_DELIMITER_SPACE = false;
    public static final boolean DEFAULT_MAKE_HYPERLINK = false;

    private final List<String> components;
    private final String delimiter;
    private final boolean multilineContent;
    private final boolean insertSpaceAfterDelimiter;
    private final String hyperlink;

    private ContentElement(Builder builder) {
        this.components = builder.components;
        this.delimiter = builder.delimiter;
        this.multilineContent = builder.multilineContent;
        this.insertSpaceAfterDelimiter = builder.insertSpaceAfterDelimiter;
        this.hyperlink = builder.hyperlink;
    }

    private String joinComponentsWithDelimiter(String delimiter) {
        return String.join(delimiter, components);
    }

    private String inline() {
        String additionalSpace = insertSpaceAfterDelimiter ? Delimiter.SPACE.getString() : Delimiter.NONE.getString();
        return joinComponentsWithDelimiter(delimiter + additionalSpace);
    }

    private String multiline() {
        return joinComponentsWithDelimiter("\\\\");      // join components with LaTeX line breaks
    }

    private String makeHyperlink(String element) {
        return new GenericCommand.Builder("href")
                .optionList(hyperlink)
                .optionBrackets(Bracket.CURLY_BRACES)
                .body(element)
                .build().getInline();
    }

    public String getContentElement() {
        String content = multilineContent ? multiline() : inline();
        if (hyperlink != null && !hyperlink.isBlank()) return makeHyperlink(content);
        return content;
    }

    @Override
    public String toString() {
        return "ContentElement{" +
                "components=" + components +
                ", delimiter='" + delimiter + '\'' +
                ", multilineContent=" + multilineContent +
                ", insertSpaceAfterDelimiter=" + insertSpaceAfterDelimiter +
                ", hyperlink='" + hyperlink + '\'' +
                '}';
    }

    public static class Builder {

        // required
        private final List<String> components;

        // optional
        private String delimiter = DEFAULT_DELIMITER;
        private boolean multilineContent = DEFAULT_MULTILINE_CONTENT;
        private boolean insertSpaceAfterDelimiter = DEFAULT_DELIMITER_SPACE;
        private String hyperlink;

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

        public Builder inlineDelimiter(Delimiter delimiter) {
            return inlineDelimiter(delimiter.getString());
        }

        public Builder multilineContent(boolean multilineContent) {
            this.multilineContent = multilineContent;
            return this;
        }

        public Builder insertSpaceAfterDelimiter(boolean insertSpaceAfterDelimiter) {
            this.insertSpaceAfterDelimiter = insertSpaceAfterDelimiter;
            return this;
        }

        public Builder makeHyperlink(String emailAddress, String defaultSubject) {
            this.hyperlink = "mailto:" + emailAddress + "?subject=" + defaultSubject;
            return this;
        }

        public Builder makeHyperlink(String url) {
            this.hyperlink = url;
            return this;
        }


        public ContentElement build() {
            return new ContentElement(this);
        }

    }
}
