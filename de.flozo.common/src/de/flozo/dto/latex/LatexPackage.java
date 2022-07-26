package de.flozo.dto.latex;

public class LatexPackage {

    private int id;
    private String name;
    private String value;
    private boolean include;

    public LatexPackage(int id, String name, String value, boolean include) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "LatexPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", include=" + include +
                '}';
    }
}
