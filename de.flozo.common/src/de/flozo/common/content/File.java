package de.flozo.common.content;

public class File {

    private int id;
    private String description;
    private String path;

    public File(int id, String description, String path) {
        this.id = id;
        this.description = description;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
