package de.flozo.dto.appearance;

public class Length {

    private int id;
    private String name;
    private double value;
    private LengthUnit unit;

    public Length(int id, String name, double value, LengthUnit unit) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.unit = unit;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    public void setUnit(LengthUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Length{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", unit=" + unit +
                '}';
    }
}
