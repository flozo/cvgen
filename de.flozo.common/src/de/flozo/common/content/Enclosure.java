package de.flozo.common.content;

public class Enclosure {

    private int id;
    private String name;
    private String file;

    public Enclosure(int id, String name, String file) {
        this.id = id;
        this.name = name;
        this.file = file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
