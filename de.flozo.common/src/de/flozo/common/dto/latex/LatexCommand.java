package de.flozo.common.dto.latex;

public class LatexCommand {

    private int id;
    private String name;
    private LatexPackage needsPackage;

    public LatexCommand(int id, String name, LatexPackage needsPackage) {
        this.id = id;
        this.name = name;
        this.needsPackage = needsPackage;
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

    public LatexPackage getNeedsPackage() {
        return needsPackage;
    }

    public void setNeedsPackage(LatexPackage needsPackage) {
        this.needsPackage = needsPackage;
    }

    @Override
    public String toString() {
        return "LatexCommand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", needsPackage=" + needsPackage +
                '}';
    }
}
