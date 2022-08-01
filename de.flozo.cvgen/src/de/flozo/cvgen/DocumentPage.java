package de.flozo.cvgen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentPage {

    private final List<DocumentElement> documentElements;

    public DocumentPage(List<DocumentElement> documentElements) {
        this.documentElements = documentElements;
    }

    public DocumentPage(DocumentElement... documentElements) {
        this(new ArrayList<>(List.of(documentElements)));
    }


    public List<String> getCode() {
        return documentElements.stream().map(DocumentElement::getElementFieldInline).collect(Collectors.toList());

    }

    @Override
    public String toString() {
        return "DocumentPage{" +
                "documentElements=" + documentElements +
                '}';
    }
}
