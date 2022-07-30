package de.flozo.common.dto.appearance;

public class SeparationSpace {

    private int id;
    private String name;
    private Length innerXSepId;
    private Length innerYSepId;
    private Length outerXSepId;
    private Length outerYSepId;

    public SeparationSpace(int id, String name, Length innerXSepId, Length innerYSepId, Length outerXSepId, Length outerYSepId) {
        this.id = id;
        this.name = name;
        this.innerXSepId = innerXSepId;
        this.innerYSepId = innerYSepId;
        this.outerXSepId = outerXSepId;
        this.outerYSepId = outerYSepId;
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

    public Length getInnerXSepId() {
        return innerXSepId;
    }

    public void setInnerXSepId(Length innerXSepId) {
        this.innerXSepId = innerXSepId;
    }

    public Length getInnerYSepId() {
        return innerYSepId;
    }

    public void setInnerYSepId(Length innerYSepId) {
        this.innerYSepId = innerYSepId;
    }

    public Length getOuterXSepId() {
        return outerXSepId;
    }

    public void setOuterXSepId(Length outerXSepId) {
        this.outerXSepId = outerXSepId;
    }

    public Length getOuterYSepId() {
        return outerYSepId;
    }

    public void setOuterYSepId(Length outerYSepId) {
        this.outerYSepId = outerYSepId;
    }

    @Override
    public String toString() {
        return "SeparationSpace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", innerXSepId=" + innerXSepId +
                ", innerYSepId=" + innerYSepId +
                ", outerXSepId=" + outerXSepId +
                ", outerYSepId=" + outerYSepId +
                '}';
    }
}
