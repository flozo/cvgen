package de.flozo.common.dto.appearance;

public class Element {

    private int id;
    private String name;
    private int elementStyleId;
    private int onPageId;
    private boolean include;

    public Element(int id, String name, int elementStyleId, int onPageId, boolean include) {
        this.id = id;
        this.name = name;
        this.elementStyleId = elementStyleId;
        this.onPageId = onPageId;
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

    public int getElementStyleId() {
        return elementStyleId;
    }

    public void setElementStyleId(int elementStyleId) {
        this.elementStyleId = elementStyleId;
    }

    public int getOnPageId() {
        return onPageId;
    }

    public void setOnPageId(int onPageId) {
        this.onPageId = onPageId;
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
                ", elementStyleId=" + elementStyleId +
                ", onPageId=" + onPageId +
                ", include=" + include +
                '}';
    }
}
