package de.flozo.common;

public class LineJoin {

    private int id;
    private String name;

    public LineJoin(int id, String name) {
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
        return "LineJoin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
