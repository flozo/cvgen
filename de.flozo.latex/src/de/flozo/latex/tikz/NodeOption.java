package de.flozo.latex.tikz;

public enum NodeOption {

    DRAW("draw"),
    FILL("fill"),
    TEXT("text"),
    ANCHOR("anchor"),
    MINIMUM_WIDTH("minimum width"),
    MINIMUM_HEIGHT("minimum height"),
    TEXT_WIDTH("text width"),
    TEXT_HEIGHT("text height"),
    TEXT_DEPTH("text depth"),
    INNER_SEP("inner sep"),
    INNER_X_SEP("inner xsep"),
    INNER_Y_SEP("inner ysep"),
    ALIGN("align"),
    FONT("font"),
    X_SHIFT("xshift"),
    Y_SHIFT("yshift"),
    LINE_WIDTH("line width"),
    LINE_CAP("line cap"),
    LINE_JOIN("line join");

    private final String nodeOption;

    NodeOption(String nodeOption) {
        this.nodeOption = nodeOption;
    }

    public String getString() {
        return nodeOption;
    }

    @Override
    public String toString() {
        return "NodeOption{" +
                "nodeOption='" + nodeOption + '\'' +
                '}';
    }

}
