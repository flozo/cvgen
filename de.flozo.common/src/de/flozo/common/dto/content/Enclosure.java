package de.flozo.common.dto.content;

public class Enclosure {

    private int id;
    private String name;
    private String caption;
    private String fileId;
    private boolean includeCaption;
    private boolean includeFile;


    public Enclosure(int id, String name, String caption, String fileId, boolean includeCaption, boolean includeFile) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.fileId = fileId;
        this.includeCaption = includeCaption;
        this.includeFile = includeFile;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isIncludeCaption() {
        return includeCaption;
    }

    public void setIncludeCaption(boolean includeCaption) {
        this.includeCaption = includeCaption;
    }

    public boolean isIncludeFile() {
        return includeFile;
    }

    public void setIncludeFile(boolean includeFile) {
        this.includeFile = includeFile;
    }

    @Override
    public String toString() {
        return "Enclosure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", caption='" + caption + '\'' +
                ", fileId='" + fileId + '\'' +
                ", includeCaption=" + includeCaption +
                ", includeFile=" + includeFile +
                '}';
    }
}
