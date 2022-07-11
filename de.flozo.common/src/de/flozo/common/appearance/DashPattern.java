package de.flozo.common.appearance;

public class DashPattern {

    private int id;
    private String name;

    public DashPattern(int id, String name) {
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
        return "DashPattern{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}