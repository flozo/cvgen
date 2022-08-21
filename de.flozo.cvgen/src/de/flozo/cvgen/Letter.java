package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.appearance.Line;
import de.flozo.common.dto.appearance.Page;
import de.flozo.db.ElementDAO;
import de.flozo.db.IconDAO;
import de.flozo.db.LineDAO;
import de.flozo.db.PageDAO;
import de.flozo.latex.assembly.IconCommand;

import java.util.List;


public class Letter {

    private final LetterTextFieldContent letterTextFieldContent;
    private final ElementDAO elementDAO;

    public Letter(LetterTextFieldContent letterTextFieldContent, ElementDAO elementDAO) {
        this.letterTextFieldContent = letterTextFieldContent;
        this.elementDAO = elementDAO;
    }

    private DocumentElement assembleDocumentElement(String name, ContentElement contentElement, Element element) {
        return new DocumentElement(name, contentElement, element);
    }

    private DocumentElement getReceiverAddressField() {
        return assembleDocumentElement("receiver_address", letterTextFieldContent.getReceiverAddressFieldContent(), elementDAO.get("address"));
    }

    private DocumentElement getBackaddressField() {
        return assembleDocumentElement("backaddress", letterTextFieldContent.getBackaddressFieldContent(), elementDAO.get("backaddress"));
    }

    private DocumentElement getDateField() {
        return assembleDocumentElement("letter_date", letterTextFieldContent.getDateFieldContent(), elementDAO.get("date"));
    }

    private DocumentElement getSubjectField() {
        return assembleDocumentElement("letter_subject", letterTextFieldContent.getSubjectFieldContent(), elementDAO.get("subject"));
    }

    private DocumentElement getBodyField() {
        return assembleDocumentElement("letter_body", letterTextFieldContent.getBodyFieldContent(), elementDAO.get("body"));
    }

    private DocumentElement getEnclosureTagLine() {
        return assembleDocumentElement("enclosures", letterTextFieldContent.getEnclosureLine(), elementDAO.get("enclosures"));
    }

    public DocumentElement getHeadline() {
        return assembleDocumentElement("headline", letterTextFieldContent.getSenderNameLineWithTitle(), elementDAO.get("headline_field"));
    }

    private DocumentElement getValediction() {
        return assembleDocumentElement("valediction", letterTextFieldContent.getValedictionLine(), elementDAO.get("valediction"));
    }

    private DocumentElement getSignature() {
        return assembleDocumentElement("signature", letterTextFieldContent.getSignature(), elementDAO.get("signature_letter"));
    }

    public DocumentPage createLetter(PageDAO pageDAO, LineDAO lineDAO, IconDAO iconDAO) {
        Page letterPage = pageDAO.get("cv_motivational_letter");

        List<Line> lineList = lineDAO.getAll();
        lineList.remove(0);
        lineList.remove(5);

        // Icons
        IconCommand mapMarkerIcon = IconCommand.fromIcon(iconDAO.get("address"));
        IconCommand phoneIcon = IconCommand.fromIcon(iconDAO.get("phone"));
        IconCommand mailIcon = IconCommand.fromIcon(iconDAO.get("mail"));

        Element senderStyle = elementDAO.get("sender");
        Element senderStyleColumn1 = elementDAO.get("sender_column1");
        Element senderStyleColumn2 = elementDAO.get("sender_column2");
        ColumnStyle column1 = new ColumnStyle(senderStyleColumn1);
        ColumnStyle column2 = new ColumnStyle(senderStyleColumn2);

        return new DocumentPage.Builder("letter", letterPage)
                .addElement(getHeadline())
                .addElement(getReceiverAddressField())
                .addElement(getBackaddressField())
                .addElement(getDateField())
                .addElement(getSubjectField())
                .addElement(getBodyField())
                .addElement(getEnclosureTagLine())
                .addMatrix(letterTextFieldContent.getSenderField(senderStyle, column1, column2, mapMarkerIcon, phoneIcon, mailIcon))
                .addElement(getValediction())
                .addElement(getSignature())
                .addLine(lineList)
                .insertLatexComments(true)
                .build();
    }


    @Override
    public String toString() {
        return "Letter{" +
                "letterTextFieldContent=" + letterTextFieldContent +
                ", elementDAO=" + elementDAO +
                '}';
    }
}
