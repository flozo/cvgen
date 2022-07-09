package de.flozo.common;

public class NamedPosition {

    private int id;
    private String name;
    private Position position;

    public NamedPosition(int id, String name, Position position) {
        this.id = id;
        this.name = name;
        this.position = position;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "NamedPosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}
