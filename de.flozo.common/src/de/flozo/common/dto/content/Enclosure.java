package de.flozo.common.dto.content;

public class Enclosure {

    private int id;
    private String name;
    private String fileId;

    public Enclosure(int id, String name, String fileId) {
        this.id = id;
        this.name = name;
        this.fileId = fileId;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
}
