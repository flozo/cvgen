package de.flozo.common.dto.content;

public class EmbeddedFile {

    private int id;
    private File file;
    private boolean include;

    public EmbeddedFile(int id, File file, boolean include) {
        this.id = id;
        this.file = file;
        this.include = include;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "EmbeddedFile{" +
                "id=" + id +
                ", file=" + file +
                ", include=" + include +
                '}';
    }
}
