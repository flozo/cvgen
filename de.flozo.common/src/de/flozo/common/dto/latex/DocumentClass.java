package de.flozo.common.dto.latex;

public class DocumentClass {

    private int id;
    private String name;
    private String value;
    private int include;
    private String options;

    public DocumentClass(int id, String name, String value, int include, String options) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.include = include;
        this.options = options;
    }

    public DocumentClass(int id, String name, String value, int include) {
        this(id, name, value, include, "");
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getInclude() {
        return include;
    }

    public void setInclude(int include) {
        this.include = include;
    }

    public String getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "DocumentClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", include=" + include +
                ", options='" + options + '\'' +
                '}';
    }
}
