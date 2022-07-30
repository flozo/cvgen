package de.flozo.common.dto.appearance;

public class Element {

    private int id;
    private String name;
    private Position position;
    private Anchor anchor;
    private Length minimumWidth;
    private Length minimumHeight;
    private SeparationSpace separationSpace;
    private ElementStyle elementStyleId;
    private int onPageId;
    private int onLayerId;
    private boolean include;

    public Element(int id, String name, Position position, Anchor anchor, Length minimumWidth, Length minimumHeight, SeparationSpace separationSpace, ElementStyle elementStyleId, int onPageId, int onLayerId, boolean include) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.anchor = anchor;
        this.minimumWidth = minimumWidth;
        this.minimumHeight = minimumHeight;
        this.separationSpace = separationSpace;
        this.elementStyleId = elementStyleId;
        this.onPageId = onPageId;
        this.onLayerId = onLayerId;
        this.include = include;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public Length getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(Length minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    public Length getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(Length minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public SeparationSpace getSeparationSpace() {
        return separationSpace;
    }

    public void setSeparationSpace(SeparationSpace separationSpace) {
        this.separationSpace = separationSpace;
    }

    public ElementStyle getElementStyleId() {
        return elementStyleId;
    }

    public void setElementStyleId(ElementStyle elementStyleId) {
        this.elementStyleId = elementStyleId;
    }

    public int getOnPageId() {
        return onPageId;
    }

    public void setOnPageId(int onPageId) {
        this.onPageId = onPageId;
    }

    public int getOnLayerId() {
        return onLayerId;
    }

    public void setOnLayerId(int onLayerId) {
        this.onLayerId = onLayerId;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", anchor=" + anchor +
                ", minimumWidth=" + minimumWidth +
                ", minimumHeight=" + minimumHeight +
                ", separationSpace=" + separationSpace +
                ", elementStyleId=" + elementStyleId +
                ", onPageId=" + onPageId +
                ", onLayerId=" + onLayerId +
                ", include=" + include +
                '}';
    }
}
