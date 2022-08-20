package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.db.ElementDAO;


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

    public DocumentElement getReceiverAddressField() {
        return assembleDocumentElement("receiver_address", letterTextFieldContent.getReceiverAddressFieldContent(), elementDAO.get("address"));
    }

    public DocumentElement getBackaddressField() {
        return assembleDocumentElement("backaddress", letterTextFieldContent.getBackaddressFieldContent(), elementDAO.get("backaddress"));
    }

    public DocumentElement getDateField() {
        return assembleDocumentElement("letter_date", letterTextFieldContent.getDateFieldContent(), elementDAO.get("date"));
    }

    public DocumentElement getSubjectField() {
        return assembleDocumentElement("letter_subject", letterTextFieldContent.getSubjectFieldContent(), elementDAO.get("subject"));
    }

    public DocumentElement getBodyField() {
        return assembleDocumentElement("letter_body", letterTextFieldContent.getBodyFieldContent(), elementDAO.get("body"));
    }

    public DocumentElement getEnclosureTagLine() {
        return assembleDocumentElement("enclosures", letterTextFieldContent.getEnclosureLine(), elementDAO.get("enclosures"));
    }

    public DocumentElement getHeadline() {
        return assembleDocumentElement("headline", letterTextFieldContent.getSenderNameLineWithTitle(), elementDAO.get("headline_field"));
    }

    public DocumentElement getValediction() {
        return assembleDocumentElement("valediction", letterTextFieldContent.getValedictionLine(), elementDAO.get("valediction"));
    }

    public DocumentElement getSignature() {
        return assembleDocumentElement("signature", letterTextFieldContent.getSignature(), elementDAO.get("signature_letter"));
    }

    @Override
    public String toString() {
        return "Letter{" +
                "letterTextFieldContent=" + letterTextFieldContent +
                ", elementDAO=" + elementDAO +
                '}';
    }
}
