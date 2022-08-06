package de.flozo.latex.tikz;

import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.FormattedExpressionList;

import java.util.ArrayList;
import java.util.List;

public class NodeStyle {

    private final List<String> optionList;

    private NodeStyle(Builder builder) {
        this.optionList = builder.optionList;
    }


    public FormattedExpressionList getOptionList() {
        return new FormattedExpressionList.Builder(optionList)
                .delimiter(Delimiter.COMMA)
                .skipLastDelimiter(true)
                .inlineSpacing(true)
                .build();
    }

    @Override
    public String toString() {
        return "NodeStyle{" +
                "optionList=" + optionList +
                '}';
    }


    public static class Builder {

        private final List<String> optionList;

        public Builder() {
            this.optionList = new ArrayList<>();
        }

        public Builder addNodeOption(NodeOption option, String value) {
            this.optionList.add(option.getString() + "=" + value);
            return this;
        }

        public Builder addCustomOption(String option) {
            this.optionList.add(option);
            return this;
        }

        public NodeStyle build() {
            return new NodeStyle(this);
        }

    }
}
