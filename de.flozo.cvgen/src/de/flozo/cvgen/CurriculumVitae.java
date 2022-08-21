package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.appearance.ItemizeStyle;
import de.flozo.common.dto.appearance.Page;
import de.flozo.common.dto.content.EmbeddedFile;
import de.flozo.common.dto.content.TimelineTextItemLink;
import de.flozo.db.*;
import de.flozo.latex.assembly.IconCommand;
import de.flozo.latex.core.Command;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.GenericCommand;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.ArrayList;
import java.util.List;

import static de.flozo.cvgen.Main.HOME_DIRECTORY;

public class CurriculumVitae {

    private final ElementDAO elementDAO;
    private final TimelineItemDAO timelineItemDAO;
    private final TextItemDAO textItemDAO;
    private final ItemizeStyleDAO itemizeStyleDAO;
    private final List<TimelineTextItemLink> textItemList;
    private final ItemizeStyle itemizeStyle;
    private final IconDAO iconDAO;
    private final LetterTextFieldContent letterTextFieldContent;
    private final EmbeddedFile photoFile;


    public CurriculumVitae(ElementDAO elementDAO, TimelineItemDAO timelineItemDAO, TextItemDAO textItemDAO, ItemizeStyleDAO itemizeStyleDAO, IconDAO iconDAO, LetterTextFieldContent letterTextFieldContent, EmbeddedFile photoFile) {
        this.elementDAO = elementDAO;
        this.timelineItemDAO = timelineItemDAO;
        this.textItemDAO = textItemDAO;
        this.itemizeStyleDAO = itemizeStyleDAO;
        this.textItemList = timelineItemDAO.getAllTextItems();
        this.itemizeStyle = itemizeStyleDAO.get("cv_blue_bullet");
        this.iconDAO = iconDAO;
        this.letterTextFieldContent = letterTextFieldContent;
        this.photoFile = photoFile;
    }

    private Timeline assembleTimeline(String timelineName, String titleEntry, String timelineEntry, String elementEntry, List<Element> columnStyle) {
        return new Timeline(timelineName,
                textItemDAO.get(titleEntry),
                elementDAO.get(titleEntry),
                timelineItemDAO.getAllIncludedOfType(timelineEntry),
                textItemList,
                itemizeStyle,
                elementDAO.get(elementEntry),
                columnStyle
        );
    }

    private List<Element> columnStyles(String dateColumnStyleEntry, String centralColumnStyleEntry, String rightColumnStyleEntry) {
        List<Element> columnStyles = new ArrayList<>();
        columnStyles.add(elementDAO.get(dateColumnStyleEntry));
        columnStyles.add(elementDAO.get(centralColumnStyleEntry));
        columnStyles.add(elementDAO.get(rightColumnStyleEntry));
        return columnStyles;
    }

    private Timeline getCareerTimeline() {
        List<Element> careerColumnStyles = columnStyles("cv_date_column", "cv_timeline_column2", "cv_timeline_column3");
        return assembleTimeline("career", "cv_career_title", "career", "cv_career", careerColumnStyles);
    }

    private Timeline getTrainingTimeline() {
        List<Element> trainingColumnStyles = columnStyles("cv_date_column", "cv_training_column2", "cv_training_column3");
        return assembleTimeline("training", "cv_further_training_title", "further_training", "cv_further_training", trainingColumnStyles);
    }

    private Timeline getEducationTimeline() {
        List<Element> educationColumnStyles = columnStyles("cv_date_column", "cv_timeline_column2", "cv_timeline_column3");
        return assembleTimeline("education", "cv_education_title", "education", "cv_education", educationColumnStyles);
    }

    private Timeline getEducationTimeline2() {
        List<Element> educationColumnStyles = columnStyles("cv_date_column", "cv_timeline_column2", "cv_timeline_column3");
        return assembleTimeline("education", "cv_education_title", "education", "cv_education_2", educationColumnStyles);
    }


    private DocumentElement getContactTitleField() {
        ContentElement cvContactTitle = new ContentElement.Builder()
                .addComponent(textItemDAO.get("cv_contact_title").getValue())
                .build();
        return new DocumentElement("cv_contact_title", cvContactTitle, elementDAO.get("cv_contact_title"));
    }

    private MatrixOfNodes assembleContactArea() {
        IconCommand mapMarkerIcon = IconCommand.fromIcon(iconDAO.get("address"));
        IconCommand phoneIcon = IconCommand.fromIcon(iconDAO.get("phone"));
        IconCommand mailIcon = IconCommand.fromIcon(iconDAO.get("mail"));
        IconCommand githubIcon = IconCommand.fromIcon(iconDAO.get("github"));
        IconCommand hyperlink = IconCommand.fromIcon(iconDAO.get("hyperlink"));
        ContentElement githubUrl = new ContentElement.Builder()
                .addComponent(textItemDAO.get("github_url").getValue())
                .addComponent("\\tiny\\raisebox{0.65ex}{" + hyperlink.getInline() + "}")
                .makeHyperlink(textItemDAO.get("github_url").getValue())
                .inlineDelimiter(Delimiter.SPACE)
                .build();
        Element cvContactStyle = elementDAO.get("cv_contact");
        ColumnStyle cvContactColumn1 = new ColumnStyle(elementDAO.get("cv_contact_column1"));
        ColumnStyle cvContactColumn2 = new ColumnStyle(elementDAO.get("cv_contact_column2"));
        return new MatrixOfNodes.Builder("cv_contact", cvContactStyle)
                .addRow(mapMarkerIcon.getInline(), letterTextFieldContent.getSenderAddressFieldNoNameContent().getInline())
                .addRow(phoneIcon.getInline(), letterTextFieldContent.getSender().getMobileNumber())
                .addRow(mailIcon.getInline(), letterTextFieldContent.getHyperlinkedEmailAddress().getInline())
                .addRow(elementDAO.get("cv_contact_url_line"), githubIcon.getInline(), githubUrl.getInline())
                .addColumnStyle(cvContactColumn1.getStyle())
                .addColumnStyle(cvContactColumn2.getStyle())
                .build();
    }

    private DocumentElement getPersonalTitleField() {
        ContentElement cvPersonalTitle = new ContentElement.Builder()
                .addComponent(textItemDAO.get("cv_personal_title").getValue())
                .build();
        return new DocumentElement("cv_personal_title", cvPersonalTitle, elementDAO.get("cv_personal_title"));
    }

    private MatrixOfNodes assemblePersonalField() {
        ColumnStyle cvContactColumn1 = new ColumnStyle(elementDAO.get("cv_contact_column1"));
        ColumnStyle cvContactColumn2 = new ColumnStyle(elementDAO.get("cv_contact_column2"));
        ContentElement personalText = new ContentElement.Builder()
                .addComponent("Geboren am")
                .addComponent(letterTextFieldContent.getSender().getPerson().getDateOfBirth())
                .addComponent("in")
                .addComponent(letterTextFieldContent.getSender().getPerson().getPlaceOfBirth())
                .inlineDelimiter(Delimiter.SPACE)
                .finalDelimiter(Delimiter.COMMA)
                .build();
        ContentElement maritalStatus = new ContentElement.Builder()
                .addComponent(letterTextFieldContent.getSender().getPerson().getNationality())
                .addComponent(letterTextFieldContent.getSender().getPerson().getMaritalStatus())
                .addComponent(letterTextFieldContent.getSender().getPerson().getChildren())
                .inlineDelimiter(Delimiter.COMMA)
                .insertSpaceAfterDelimiter(true)
                .build();
        Element cvPersonalStyle = elementDAO.get("cv_personal");
        return new MatrixOfNodes.Builder("cv_personal", cvPersonalStyle)
                .addRow("", personalText.getInline())
                .addRow("", maritalStatus.getInline())
                .addColumnStyle(cvContactColumn1.getStyle())
                .addColumnStyle(cvContactColumn2.getStyle())
                .build();
    }

    private DocumentElement photo() {
        String absoluteFilePathPhoto = photoFile.getFile().getPath().replaceFirst("^~", HOME_DIRECTORY);
        ContentElement photoOption = new ContentElement.Builder()
                .addComponent("scale")
                .addComponent(String.valueOf(photoFile.getScaleFactor()))
                .inlineDelimiter(Delimiter.EQUALS)
                .build();
        Command includePhoto = new GenericCommand.Builder("includegraphics")
                .optionList(photoOption.getInline())
                .body(absoluteFilePathPhoto)
                .build();
        ContentElement includegraphicsPhoto = new ContentElement.Builder(includePhoto.getInline()).build();
        return new DocumentElement("photo", includegraphicsPhoto, elementDAO.get("cv_photo"));
    }

    private DocumentElement cvTitle() {
        ContentElement cvTitle = new ContentElement.Builder()
                .addComponent(textItemDAO.get("cv_title").getValue())
                .build();
        return new DocumentElement("cv_title", cvTitle, elementDAO.get("cv_title"));
    }

    private DocumentPage.Builder buildCVPage(String pageName, Page page, RectangleDAO rectangleDAO, LineDAO lineDAO, DocumentElement headline) {
        return new DocumentPage.Builder(pageName, page)
                .addRectangle(rectangleDAO.get("cv_left_box"))
                .addLine(lineDAO.get("cv_box_right_border"))
                .addLine(lineDAO.get("headline_separation"))
                .addElement(headline);
    }

    public DocumentPage createCVPage1(PageDAO pageDAO, LineDAO lineDAO, RectangleDAO rectangleDAO, DocumentElement headline) {
        Page cvPage1 = pageDAO.get("cv_page_1");
        return buildCVPage("cv1", cvPage1,rectangleDAO,lineDAO,headline)
                .addElement(photo())
                .addElement(getContactTitleField())
                .addMatrix(assembleContactArea())
                .addElement(getPersonalTitleField())
                .addMatrix(assemblePersonalField())
                .addElement(getTrainingTimeline().getTitleField())
                .addMatrix(getTrainingTimeline().getItemMatrix(elementDAO.get("cv_timeline_headline_compact"), elementDAO.get("cv_item_lists")))
                .addElement(getCareerTimeline().getTitleField())
                .addMatrix(getCareerTimeline().getItemMatrix(elementDAO.get("cv_timeline_headline"), elementDAO.get("cv_item_lists")))
                .addElement(getEducationTimeline().getTitleField())
                .addMatrix(getEducationTimeline().getItemMatrix(0, 0, elementDAO.get("cv_timeline_headline"), elementDAO.get("cv_item_lists")))
                .insertLatexComments(true)
                .build();
    }

    public DocumentPage createCVPage2(PageDAO pageDAO, LineDAO lineDAO, RectangleDAO rectangleDAO, DocumentElement headline) {
        Page cvPage2 = pageDAO.get("cv_page_2");
        return buildCVPage("cv2", cvPage2, rectangleDAO, lineDAO, headline)
                .addMatrix(getEducationTimeline2().getItemMatrix(1, 3, elementDAO.get("cv_timeline_headline"), elementDAO.get("cv_item_lists")))
                .insertLatexComments(true)
                .build();
    }

    @Override
    public String toString() {
        return "CurriculumVitae{" +
                "elementDAO=" + elementDAO +
                ", timelineItemDAO=" + timelineItemDAO +
                ", textItemDAO=" + textItemDAO +
                ", itemizeStyleDAO=" + itemizeStyleDAO +
                ", textItemList=" + textItemList +
                ", itemizeStyle=" + itemizeStyle +
                ", iconDAO=" + iconDAO +
                ", letterTextFieldContent=" + letterTextFieldContent +
                ", photoFile=" + photoFile +
                '}';
    }
}
