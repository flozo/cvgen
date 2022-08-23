package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.Map;

public class SkillArea {

    private final Map<String, String> skills;
    private final Element titleStyle;
    private final Element matrixStyle;
    private final Element column1Style;
    private final Element column2Style;

    public SkillArea(Map<String, String> skills, Element titleStyle, Element matrixStyle, Element column1Style, Element column2Style) {
        this.skills = skills;
        this.titleStyle = titleStyle;
        this.matrixStyle = matrixStyle;
        this.column1Style = column1Style;
        this.column2Style = column2Style;
    }

    public DocumentElement getSkillTitleField(String title) {
        ContentElement cvContactTitle = new ContentElement.Builder()
                .addComponent(title)
                .build();
        return new DocumentElement("cv_skill_title", cvContactTitle, titleStyle);
    }


    private MatrixOfNodes.Builder skeleton() {
        ColumnStyle column1 = new ColumnStyle(column1Style);
        ColumnStyle column2 = new ColumnStyle(column2Style);
        return new MatrixOfNodes.Builder("skills", matrixStyle)
                .addColumnStyle(column1.getStyle())
                .addColumnStyle(column2.getStyle());
    }

    public MatrixOfNodes assembleSkillArea() {
        MatrixOfNodes.Builder skillMatrix = skeleton();
        for (Map.Entry<String, String> entry : skills.entrySet()) {
            skillMatrix.addRow(entry.getKey(), entry.getValue());
        }
        return skillMatrix.build();
    }

    @Override
    public String toString() {
        return "SkillArea{" +
                "skills=" + skills +
                ", titleStyle=" + titleStyle +
                ", matrixStyle=" + matrixStyle +
                ", column1Style=" + column1Style +
                ", column2Style=" + column2Style +
                '}';
    }
}
