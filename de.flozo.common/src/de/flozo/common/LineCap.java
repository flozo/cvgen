package de.flozo.common;

public class LineCap {

    private int id;
    private String name;

    public LineCap(int id, String name) {
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
        return "LineCap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
