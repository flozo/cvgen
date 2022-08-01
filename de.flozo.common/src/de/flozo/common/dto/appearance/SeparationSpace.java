package de.flozo.common.dto.appearance;

public class SeparationSpace {

    private int id;
    private String name;
    private Length innerXSep;
    private Length innerYSep;
    private Length outerXSep;
    private Length outerYSep;

    public SeparationSpace(int id, String name, Length innerXSep, Length innerYSep, Length outerXSep, Length outerYSep) {
        this.id = id;
        this.name = name;
        this.innerXSep = innerXSep;
        this.innerYSep = innerYSep;
        this.outerXSep = outerXSep;
        this.outerYSep = outerYSep;
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

    public Length getInnerXSep() {
        return innerXSep;
    }

    public void setInnerXSep(Length innerXSep) {
        this.innerXSep = innerXSep;
    }

    public Length getInnerYSep() {
        return innerYSep;
    }

    public void setInnerYSep(Length innerYSep) {
        this.innerYSep = innerYSep;
    }

    public Length getOuterXSep() {
        return outerXSep;
    }

    public void setOuterXSep(Length outerXSep) {
        this.outerXSep = outerXSep;
    }

    public Length getOuterYSep() {
        return outerYSep;
    }

    public void setOuterYSep(Length outerYSep) {
        this.outerYSep = outerYSep;
    }

    @Override
    public String toString() {
        return "SeparationSpace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", innerXSep=" + innerXSep +
                ", innerYSep=" + innerYSep +
                ", outerXSep=" + outerXSep +
                ", outerYSep=" + outerYSep +
                '}';
    }
}
