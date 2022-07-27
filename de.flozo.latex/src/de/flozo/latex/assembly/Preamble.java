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


    private Preamble(DocumentClass documentClass, Map<LatexPackage, ExpressionList> packageMap) {
        this.documentClass = documentClass;
        this.packageMap = packageMap;
    }


    public static Preamble create(DocumentClass documentClass, PackageList packageList) {
        return new Preamble(documentClass, packageList.getOptionMap());
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

    public List<String> getPreambleCode() {
        List<String> preambleLines = new ArrayList<>();
        preambleLines.add(getDocumentClassCommand());
        preambleLines.addAll(getUsepackageBlock());
        return preambleLines;
    }


    @Override
    public String toString() {
        return "Preamble{" +
                "documentClass=" + documentClass +
                ", packageMap=" + packageMap +
                '}';
    }
}
