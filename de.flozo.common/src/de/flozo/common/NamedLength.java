package de.flozo.common;

public class NamedLength {

    private int id;
    private String name;
    private Length length;

    public NamedLength(int id, String name, Length length) {
        this.id = id;
        this.name = name;
        this.length = length;
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

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "NamedLength{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
