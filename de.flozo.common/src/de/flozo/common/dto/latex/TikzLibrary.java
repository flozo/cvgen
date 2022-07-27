package de.flozo.common.dto.latex;

public class TikzLibrary {

    private int id;
    private String name;
    private boolean include;
    private String description;

    public TikzLibrary(int id, String name, boolean include, String description) {
        this.id = id;
        this.name = name;
        this.include = include;
        this.description = description;
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

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TikzLibrary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", include=" + include +
                ", description='" + description + '\'' +
                '}';
    }
}
