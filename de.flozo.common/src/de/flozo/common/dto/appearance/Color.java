package de.flozo.common.dto.appearance;

public class Color {

    private int id;
    private String specifier;

    public Color(int id, String specifier) {
        this.id = id;
        this.specifier = specifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", specifier='" + specifier + '\'' +
                '}';
    }
}
