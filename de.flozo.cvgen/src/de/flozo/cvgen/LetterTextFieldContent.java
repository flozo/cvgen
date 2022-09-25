package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.content.Address;
import de.flozo.common.dto.content.EmbeddedFile;
import de.flozo.common.dto.content.Enclosure;
import de.flozo.common.dto.content.LetterContent;
import de.flozo.latex.assembly.IconCommand;
import de.flozo.latex.core.Command;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.GenericCommand;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.List;
import java.util.stream.Collectors;

import static de.flozo.cvgen.Main.HOME_DIRECTORY;

public class LetterTextFieldContent {

    public static final String DEFAULT_BACKADDRESS_SEPARATOR = "\\hspace{8pt}$\\bullet$\\hspace{8pt}";
    public static final String ENCLOSURE_TAG = "Enclosure";

    private final LetterContent letterContent;
    private final Address sender;
    private final Address receiver;
    private final List<Enclosure> enclosures;
    private final String backaddressSeparator;
    private final EmbeddedFile signatureFile;
    private final String absoluteFilePathSignature;

    public LetterTextFieldContent(LetterContent letterContent, List<Enclosure> enclosures, String backaddressSeparator, EmbeddedFile signatureFile) {
        this.letterContent = letterContent;
        this.sender = letterContent.getSender();
        this.receiver = letterContent.getReceiver();
        this.enclosures = enclosures;
        this.backaddressSeparator = backaddressSeparator;
        this.signatureFile = signatureFile;
        this.absoluteFilePathSignature = signatureFile.getFile().getPath().replaceFirst("^~", HOME_DIRECTORY);
    }

    public LetterTextFieldContent(LetterContent letterContent, List<Enclosure> enclosures, EmbeddedFile signatureFile) {
        this(letterContent, enclosures, DEFAULT_BACKADDRESS_SEPARATOR, signatureFile);
    }

    private ContentElement assembleName(Address contact) {
        ContentElement.Builder nameLineBuilder = new ContentElement.Builder();
        if (contact.getCompany() != null && !contact.getCompany().isBlank()) {
            nameLineBuilder.addComponent(contact.getCompany());
        } else {
            nameLineBuilder
                    .addComponent(contact.getPerson().getFirstName())
                    .addComponent(contact.getPerson().getLastName());
        }
        return nameLineBuilder.inlineDelimiter(Delimiter.SPACE).build();
    }

    public ContentElement getSenderNameLine() {
        return assembleName(sender);
    }

    private ContentElement assembleStreetLine(Address contact) {
        return new ContentElement.Builder()
                .addComponent(contact.getStreet())
                .addComponent(contact.getHouseNumber())
                .inlineDelimiter(Delimiter.SPACE)
                .build();
    }

    private ContentElement assembleCityLine(Address contact) {
        return new ContentElement.Builder()
                .addComponent(contact.getPostalCode())
                .addComponent(contact.getCity())
                .inlineDelimiter(Delimiter.SPACE)
                .build();
    }

    private ContentElement assembleAddressField(Address contact) {
        return new ContentElement.Builder()
                .addComponent(assembleName(contact).getInline())
                .addComponent(assembleStreetLine(contact).getInline())
                .addComponent(assembleCityLine(contact).getInline())
                .multilineContent(true)
                .build();
    }

    private ContentElement assembleAddressFieldNoName(Address contact) {
        return new ContentElement.Builder()
                .addComponent(assembleStreetLine(contact).getInline())
                .addComponent(assembleCityLine(contact).getInline())
                .multilineContent(true)
                .build();
    }

    public ContentElement getSenderAddressFieldContent() {
        return assembleAddressField(sender);
    }

    public ContentElement getSenderAddressFieldNoNameContent() {
        return assembleAddressFieldNoName(sender);
    }

    public ContentElement getReceiverAddressFieldContent() {
        return assembleAddressField(receiver);
    }

    public ContentElement getSenderNameLineWithTitle() {
        return new ContentElement.Builder()
                .addComponent(sender.getPerson().getAcademicTitle() + ".")
                .addComponent(sender.getPerson().getFirstName())
                .addComponent(sender.getPerson().getLastName())
                .inlineDelimiter(Delimiter.NON_BREAKING_SPACE)
                .build();
    }

    public ContentElement getBackaddressFieldContent() {
        return new ContentElement.Builder()
                .addComponent(assembleName(sender).getInline())
                .addComponent(assembleStreetLine(sender).getInline())
                .addComponent(assembleCityLine(sender).getInline())
                .inlineDelimiter(backaddressSeparator)
                .build();
    }

    public ContentElement getDateFieldContent() {
        return new ContentElement.Builder()
                .addComponent(sender.getCity())
                .addComponent(letterContent.getDate())
                .insertSpaceAfterDelimiter(true)
                .inlineDelimiter(Delimiter.COMMA)
                .build();
    }

    public ContentElement getSubjectFieldContent() {
        return new ContentElement.Builder()
                .addComponent(letterContent.getSubject())
                .build();
    }

    public ContentElement getBodyFieldContent() {
        return new ContentElement.Builder()
                .addComponent(letterContent.getBodyText())
                .build();
    }

    private ContentElement assembleEnclosuresField() {
        return new ContentElement.Builder()
                .addComponents(enclosures.stream().map(Enclosure::getCaption).collect(Collectors.toList()))
                .inlineDelimiter(Delimiter.COMMA)
                .insertSpaceAfterDelimiter(true)
                .build();
    }

    public ContentElement getEnclosureLine() {
        if (enclosures.size() == 0) {
            return new ContentElement.Builder().build();
        }
        return new ContentElement.Builder()
                .addComponent(ENCLOSURE_TAG + ": ")
                .addComponent(assembleEnclosuresField().getInline())
                .build();
    }

    public ContentElement getValedictionLine() {
        return new ContentElement.Builder()
                .addComponent(letterContent.getValediction().getValue())
                .build();
    }


    private ContentElement assembleSignatureOption() {
        return new ContentElement.Builder()
                .addComponent("scale")
                .addComponent(String.valueOf(signatureFile.getScaleFactor()))
                .inlineDelimiter(Delimiter.EQUALS)
                .build();
    }

    private Command assembleIncludegraphics() {
        return new GenericCommand.Builder("includegraphics")
                .optionList(assembleSignatureOption().getInline())
                .body(absoluteFilePathSignature)
                .build();
    }

    public ContentElement getSignature() {
        return new ContentElement.Builder(assembleIncludegraphics().getInline())
                .addComponent(assembleName(sender).getInline())
                .inlineDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .build();
    }

    public Address getSender() {
        return sender;
    }

    public ContentElement getHyperlinkedEmailAddress() {
        return new ContentElement.Builder()
                .addComponent(getSender().getEMailAddress())
                .makeHyperlink(getSender().getEMailAddress(), getSubjectFieldContent().getInline())
                .build();
    }

    public MatrixOfNodes getSenderField(Element senderFieldStyle, ColumnStyle column1, ColumnStyle column2, IconCommand addressIcon, IconCommand phoneIcon, IconCommand mailIcon) {
        return new MatrixOfNodes.Builder("sender_field", senderFieldStyle)
                .addRow(assembleAddressFieldNoName(sender).getInline(), addressIcon.getInline())
                .addRow(getSender().getMobileNumber(), phoneIcon.getInline())
                .addRow(getHyperlinkedEmailAddress().getInline(), mailIcon.getInline())
                .addColumnStyle(column1.getStyle())
                .addColumnStyle(column2.getStyle())
                .build();
    }

    @Override
    public String toString() {
        return "LetterTextFieldContent{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", letterContent=" + letterContent +
                ", enclosures=" + enclosures +
                ", backaddressSeparator='" + backaddressSeparator + '\'' +
                ", signatureFile=" + signatureFile +
                ", absoluteFilePathSignature='" + absoluteFilePathSignature + '\'' +
                '}';
    }
}
