package de.flozo.common.dto.content;

public class Enclosure {

    private int id;
    private String name;
    private String caption;
    private File file;
    private boolean includeCaption;
    private boolean includeFile;

    public Enclosure(int id, String name, String caption, File file, boolean includeCaption, boolean includeFile) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.file = file;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
                ", file=" + file +
                ", includeCaption=" + includeCaption +
                ", includeFile=" + includeFile +
                '}';
    }
}
