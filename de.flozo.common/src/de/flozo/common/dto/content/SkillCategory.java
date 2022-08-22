package de.flozo.common.dto.content;

public class SkillCategory {

    private int id;
    private String name;
    private String label;
    private SkillType skillType;

    public SkillCategory(int id, String name, String label, SkillType skillType) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.skillType = skillType;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    @Override
    public String toString() {
        return "SkillCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", skillType=" + skillType +
                '}';
    }
}
