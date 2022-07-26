package de.flozo.dto.latex;

public class PackageOption {

    private int id;
    private String optionString;
    private LatexPackage latexPackage;
    private String description;
    private boolean include;

    public PackageOption(int id, String optionString, LatexPackage latexPackage, String description, boolean include) {
        this.id = id;
        this.optionString = optionString;
        this.latexPackage = latexPackage;
        this.description = description;
        this.include = include;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionString() {
        return optionString;
    }

    public void setOptionString(String optionString) {
        this.optionString = optionString;
    }

    public LatexPackage getLatexPackage() {
        return latexPackage;
    }

    public void setLatexPackage(LatexPackage latexPackage) {
        this.latexPackage = latexPackage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "PackageOption{" +
                "id=" + id +
                ", optionString='" + optionString + '\'' +
                ", latexPackage=" + latexPackage +
                ", description='" + description + '\'' +
                ", include=" + include +
                '}';
    }
}
