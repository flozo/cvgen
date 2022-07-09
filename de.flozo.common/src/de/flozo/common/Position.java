package de.flozo.common;

public class Position {

    private Length x;
    private Length y;

    public Position(Length x, Length y) {
        this.x = x;
        this.y = y;
    }

    public Length getX() {
        return x;
    }

    public void setX(Length x) {
        this.x = x;
    }

    public Length getY() {
        return y;
    }

    public void setY(Length y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
