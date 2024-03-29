package de.flozo.common.dto.appearance;

public class PredefinedOpacity {

    private int id;
    private String value;

    public PredefinedOpacity(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PredefinedOpacity{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
