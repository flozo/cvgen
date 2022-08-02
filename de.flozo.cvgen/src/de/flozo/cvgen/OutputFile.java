package de.flozo.cvgen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class OutputFile {


    private final String outputDirectory;
    private final String fileNameLatex;
    private final String versionInfo;
    private final List<String> fileContent;
    private final String fileExtension = "pdf";
    private final String latexCommand = "pdflatex";
    private final String option1 = "-synctex=1";
    private final String option2 = "-interaction=nonstopmode";
    private final String option3 = "-output-directory";
    private final String fileNameOutputLog = "pdfLaTeX_last_output.log";
    private final String fileNameErrorLog = "pdfLaTeX_last_error.log";


    public OutputFile(String outputDirectory, String fileNameLatex, String versionInfo, List<String> fileContent) {
        this.outputDirectory = outputDirectory;
        this.fileNameLatex = fileNameLatex;
        this.versionInfo =  versionInfo;
        this.fileContent = fileContent;
    }


    public boolean create(boolean runPDFLatex, boolean openPDF) {
        Path pathOfTexFile = Paths.get(outputDirectory, fileNameLatex);
        Path pathOfOutputLogFile = Paths.get(outputDirectory, fileNameOutputLog);
        Path pathOfErrorLogFile = Paths.get(outputDirectory, fileNameErrorLog);
        if (writeToFile(fileContent, pathOfTexFile.toString())) {
            System.out.println("[output] ... success!");
        } else {
            return false;
        }
        if (runPDFLatex || openPDF) {
            if (runPDFLatex(outputDirectory, fileNameLatex)) {
                System.out.println("[output] ... success!");
            } else {
                return false;
            }
        }
        if (openPDF) {
            if (openPDF(removeFileExtension(pathOfTexFile.toString()) + "." + fileExtension)) {
                System.out.println("[output] ... success!");
            } else {
                return false;
            }
        }
        return true;
    }


    private boolean writeToFile(List<String> codeLines, String outputFile) {
        System.out.println("[output] Writing LaTeX code to file \"" + outputFile + "\" ...");
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            printWriter.println(versionInfo);
            for (String codeLine : codeLines) {
                printWriter.println(codeLine);
            }
            return true;
        } catch (IOException e) {
            System.out.println("[output] ... failed to write to file!");
            return false;
        }
    }

    private boolean runPDFLatex(String outputDirectory, String outputFile) {
        ProcessBuilder processBuilder = new ProcessBuilder(latexCommand, option1, option2, option3, outputDirectory, outputFile);
        processBuilder.redirectErrorStream(true);
        File outputLog = new File(outputDirectory, fileNameOutputLog);
        File errorLog = new File(outputDirectory, fileNameErrorLog);
        processBuilder.redirectOutput(outputLog);
        processBuilder.redirectError(errorLog);
        String command = "\"" + String.join(" ", processBuilder.command()) + "\"";
        System.out.println("[output] Run command " + command + " ...");
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new LatexCompilationException("[output] [LatexCompilationException] Error in LaTeX code!");
            }
            return true;
        } catch (IOException e) {
            System.out.println("[output] [IOException] ... failed to run pdflatex!");
            return false;
        } catch (InterruptedException e) {
            System.out.println("[output] [InterruptedException] ... failed to run pdflatex!");
            return false;
        } catch (LatexCompilationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean openPDF(String pdfFile) {
        if (!Desktop.isDesktopSupported()) {
            System.out.println("[output] [error] Opening PDF with default viewer is not supported on your platform!");
            return false;
        }
        System.out.println("[output] Open PDF file with default viewer ...");
        File pdf = new File(pdfFile);
        try {
            Desktop.getDesktop().open(pdf);
            return true;
        } catch (IOException e) {
            System.out.println();
            System.out.println("[output] ... failed to open PDF file!");
            return false;
        }
    }


    private String removeFileExtension(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        } else {
            return fileName;
        }
    }


}
