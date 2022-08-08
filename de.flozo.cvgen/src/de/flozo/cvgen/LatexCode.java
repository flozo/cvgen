package de.flozo.cvgen;

import de.flozo.latex.assembly.Preamble;
import de.flozo.latex.core.Environment;

import java.util.ArrayList;
import java.util.List;

public class LatexCode {

    private final String versionInfoComment;
    private final Preamble preamble;
    private final Environment documentEnvironment;

    public LatexCode(String versionInfoComment, Preamble preamble, Environment documentEnvironment) {
        this.versionInfoComment = versionInfoComment;
        this.preamble = preamble;
        this.documentEnvironment = documentEnvironment;
    }

    public List<String> getCode() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add(versionInfoComment);
        codeLines.addAll(preamble.getPreambleCode());
        codeLines.addAll(documentEnvironment.getBlock());
        return codeLines;
    }

    @Override
    public String toString() {
        return "LaTeXCode{" +
                "versionInfoComment='" + versionInfoComment + '\'' +
                ", preamble=" + preamble +
                ", documentEnvironment=" + documentEnvironment +
                '}';
    }
}
