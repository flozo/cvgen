package de.flozo.common.dto.appearance;

public class IconCategory {

    private int id;
    private String name;

    public IconCategory(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "IconCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
