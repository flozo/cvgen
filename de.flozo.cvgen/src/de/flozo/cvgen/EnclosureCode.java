package de.flozo.cvgen;

import de.flozo.common.dto.content.Enclosure;
import de.flozo.latex.core.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.flozo.cvgen.Main.HOME_DIRECTORY;

public class EnclosureCode {

    private final List<Enclosure> enclosureList;

    public EnclosureCode(List<Enclosure> enclosureList) {
        this.enclosureList = enclosureList;
    }

    private Environment getTikzPictureEnvironment(String body) {
        return new Environment.Builder(EnvironmentName.TIKZPICTURE)
                .body(body)
                .build();
    }

    private int countPdfPages(String fileName) {
        int pageCount = 0;
        try (PDDocument document = PDDocument.load(new File(fileName))) {
            pageCount += document.getNumberOfPages();
        } catch (IOException e) {
            System.out.println("[enclosures] Failed to read enclosure file \"" + fileName + "\"");
        }
        return pageCount;
    }

    private String getAbsoluteFilePath(String file) {
        return file.replaceFirst("^~", HOME_DIRECTORY);
    }

    private ContentElement pdf(int page, String file) {
        ContentElement option = new ContentElement.Builder()
                .addComponent("page")
                .addComponent(String.valueOf(page))
                .inlineDelimiter(Delimiter.EQUALS)
                .build();
        Command includePdf = new GenericCommand.Builder("includegraphics")
                .optionList(option.getInline())
                .body(file)
                .build();
        return new ContentElement.Builder(includePdf.getInline()).build();
    }

    private List<Environment> getEnvironments() {
        List<Environment> tikzPictureEnvironments = new ArrayList<>();
        for (Enclosure enclosure : enclosureList) {
            String absoluteFilePath = getAbsoluteFilePath(enclosure.getFile().getPath());
            for (int page = 1; page <= countPdfPages(absoluteFilePath); page++) {
                tikzPictureEnvironments.add(getTikzPictureEnvironment("\\node [inner sep=0pt] at (current page.center) {" + pdf(page, absoluteFilePath).getInline() + "};"));
            }
        }
        return tikzPictureEnvironments;
    }

    public List<String> getCode() {
        List<String> lines = new ArrayList<>();
        for (Environment environment : getEnvironments()) {
            lines.addAll(environment.getBlock());
        }
        return lines;
    }


    @Override
    public String toString() {
        return "EnclosureCode{" +
                "enclosureList=" + enclosureList +
                '}';
    }
}
