package de.flozo.common.dto.content;

public class Skill {

    private int id;
    private String name;
    private String description;
    private int skillLevelActual;
    private int skillLevelMax;
    private SkillCategory skillCategory;

    public Skill(int id, String name, String description, int skillLevelActual, int skillLevelMax, SkillCategory skillCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skillLevelActual = skillLevelActual;
        this.skillLevelMax = skillLevelMax;
        this.skillCategory = skillCategory;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSkillLevelActual() {
        return skillLevelActual;
    }

    public void setSkillLevelActual(int skillLevelActual) {
        this.skillLevelActual = skillLevelActual;
    }

    public int getSkillLevelMax() {
        return skillLevelMax;
    }

    public void setSkillLevelMax(int skillLevelMax) {
        this.skillLevelMax = skillLevelMax;
    }

    public SkillCategory getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", skillLevelActual=" + skillLevelActual +
                ", skillLevelMax=" + skillLevelMax +
                ", skillCategory=" + skillCategory +
                '}';
    }
}
