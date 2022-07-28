package de.flozo.latex.assembly;

import de.flozo.common.dto.latex.DocumentClass;
import de.flozo.common.dto.latex.LatexPackage;
import de.flozo.latex.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Preamble {

    public static final String KEYWORD_DOCUMENTCLASS = "documentclass";
    private final DocumentClass documentClass;
    private final Map<LatexPackage, ExpressionList> packageMap;
    private final List<String> tikzLibraries;
    private final List<String> hyperOptions;


    private Preamble(DocumentClass documentClass, Map<LatexPackage, ExpressionList> packageMap, List<String> tikzLibraries, List<String> hyperOptions) {
        this.documentClass = documentClass;
        this.packageMap = packageMap;
        this.tikzLibraries = tikzLibraries;
        this.hyperOptions = hyperOptions;
    }


    public static Preamble create(DocumentClass documentClass, PackageList packageList, List<String> tikzLibraries, List<String> hyperOptions) {
        return new Preamble(documentClass, packageList.getOptionMap(), tikzLibraries, hyperOptions);
    }

    private String getDocumentClassCommand() {
        return new GenericCommand.Builder(KEYWORD_DOCUMENTCLASS)
                .optionList(documentClass.getOptions())
                .body(documentClass.getValue())
                .optionBrackets(Bracket.SQUARE_BRACKETS)
                .bodyBrackets(Bracket.CURLY_BRACES)
                .optionDelimiter(Delimiter.COMMA)
                .build().getInline();
    }

    private List<String> getUsepackageBlock() {
        List<String> codeLines = new ArrayList<>();
        for (Map.Entry<LatexPackage, ExpressionList> entry : packageMap.entrySet()) {
            UsepackageCommand usepackageCommand = new UsepackageCommand(entry.getKey(), entry.getValue());
            codeLines.add(usepackageCommand.getInline());
        }
        return codeLines;
    }

    private String getUseTikzLibraryCommand() {
        return new GenericCommand.Builder("usetikzlibrary")
                .body(tikzLibraries)
                .bodyDelimiter(Delimiter.COMMA)
                .build().getInline();
    }

    private String getStandaloneEnvironmentCommand() {
        return new GenericCommand.Builder("standaloneenv")
                .body("tikzpicture")
                .build().getInline();
    }

    private List<String> getHypersetupCommand() {
        return new GenericCommand.Builder("hypersetup")
                .body(hyperOptions)
                .bodyDelimiter(Delimiter.COMMA)
                .indentBody(true)
                .build().getBlock();
    }

    public List<String> getPreambleCode() {
        List<String> preambleLines = new ArrayList<>();
        preambleLines.add(getDocumentClassCommand());
        preambleLines.addAll(getUsepackageBlock());
        preambleLines.add(getUseTikzLibraryCommand());
        preambleLines.add(getStandaloneEnvironmentCommand());
        preambleLines.addAll(getHypersetupCommand());
        return preambleLines;
    }


    @Override
    public String toString() {
        return "Preamble{" +
                "documentClass=" + documentClass +
                ", packageMap=" + packageMap +
                ", tikzLibraries=" + tikzLibraries +
                ", hyperOptions=" + hyperOptions +
                '}';
    }
}
