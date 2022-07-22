package de.flozo.dto.appearance;

public class Position {

    private int id;
    private String name;
    private Length lengthX;
    private Length lengthY;

    public Position(int id, String name, Length lengthX, Length lengthY) {
        this.id = id;
        this.name = name;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
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

    public Length getLengthX() {
        return lengthX;
    }

    public void setLengthX(Length lengthX) {
        this.lengthX = lengthX;
    }

    public Length getLengthY() {
        return lengthY;
    }

    public void setLengthY(Length lengthY) {
        this.lengthY = lengthY;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lengthX=" + lengthX +
                ", lengthY=" + lengthY +
                '}';
    }
}
