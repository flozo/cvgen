package de.flozo.latex.tikz;


import de.flozo.common.dto.appearance.Anchor;
import de.flozo.common.dto.appearance.Color;
import de.flozo.common.dto.appearance.FontSize;
import de.flozo.latex.core.Delimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOfNodes {

    private final String name;
    private final List<List<Node>> matrix;
    private final List<String> columnStyles;


    private final Point position;

    private final Anchor anchor;
    private final FontSize fontSize;
    private final Color backgroundColor;
    private final Color borderColor;
    private final Color textColor;


    public MatrixOfNodes(Builder builder) {
        this.name = builder.name;
        this.matrix = builder.matrix;
        this.columnStyles = builder.columnStyles;
        this.position = builder.position;
        this.anchor = builder.anchor;
        this.fontSize = builder.fontSize;
        this.backgroundColor = builder.backgroundColor;
        this.borderColor = builder.borderColor;
        this.textColor = builder.textColor;
    }


    public List<String> getBlock() {
        return buildNode().getBlock();
    }

    private Node buildNode() {
        Node.Builder node = new Node.Builder(assembleTable())
                .name(name)
                .position(position)
                .isMatrix(true)
                .anchor(anchor)
                .fontSize(fontSize)
                .fillColor(backgroundColor)
                .drawColor(borderColor)
                .textColor(textColor)
                .bodyDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .skipLastDelimiter(false);
        for (String columnStyle : columnStyles) {
            node.addCustomOption(columnStyle);
        }
        return node.build();
    }


    private List<String> assembleTable() {
        List<String> matrixLines = new ArrayList<>();
        for (int row = 0; row < getNumRows(); row++) {
            List<Node> rowNodes = matrix.get(row);
            List<String> rowStrings = rowNodes.stream().map(Node::getInline).collect(Collectors.toList());
            matrixLines.add(String.join(" & ", rowStrings));
        }
        return matrixLines;
    }

    public int getNumCols() {
        return matrix.get(0).size();
    }

    public int getNumRows() {
        return matrix.size();
    }

    @Override
    public String toString() {
        return "MatrixOfNodes{" +
                "name='" + name + '\'' +
                ", matrix=" + matrix +
                ", columnStyles=" + columnStyles +
                ", position=" + position +
                ", anchor=" + anchor +
                ", fontSize=" + fontSize +
                ", backgroundColor=" + backgroundColor +
                ", borderColor=" + borderColor +
                ", textColor=" + textColor +
                '}';
    }

    public static class Builder {

        // required
        private final String name;
        private final List<List<Node>> matrix = new ArrayList<>();
        private final List<String> columnStyles = new ArrayList<>();
        private final Point position;
        private final Anchor anchor;

        // optional
        private FontSize fontSize;
        private Color backgroundColor;
        private Color borderColor;
        private Color textColor;


        public Builder(String name, Point position, Anchor anchor) {
            this.name = name;
            this.position = position;
            this.anchor = anchor;
        }

        public Builder addRow(String... row) {
            return addRow(new ArrayList<>(List.of(row)));
        }

        public Builder addRow(List<String> row) {
            return addRowOfNodes(row.stream()
                    .map(e -> new Node.Builder(e).build())
                    .collect(Collectors.toList()));
        }

        public Builder addRowOfNodes(Node... row) {
            return addRowOfNodes(new ArrayList<>(List.of(row)));
        }

        public Builder addRowOfNodes(List<Node> row) {
            this.matrix.add(row);
            return this;
        }

        public Builder addColumnStyle(NodeStyle nodeStyle) {
            int columnStyleNumber = columnStyles.size() + 1;
            this.columnStyles.add("column " + columnStyleNumber + "/.style={nodes={" + nodeStyle.getOptionList().getInline() + "}}");
            return this;
        }

        public Builder fontSize(FontSize fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder backgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder borderColor(Color borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        public Builder textColor(Color textColor) {
            this.textColor = textColor;
            return this;
        }


        public MatrixOfNodes build() {
            return new MatrixOfNodes(this);
        }

    }

}
