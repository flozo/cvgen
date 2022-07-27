package de.flozo.latex.core;

import de.flozo.common.dto.latex.LatexPackage;

import java.util.List;

public class UsepackageCommand implements Command {

    public static final String KEYWORD = "usepackage";
    private final LatexPackage packageName;
    private final ExpressionList options;

    public UsepackageCommand(LatexPackage packageName, ExpressionList options) {
        this.packageName = packageName;
        this.options = options;
    }

    @Override
    public List<String> getInlineOptions() {
        return new GenericCommand.Builder(KEYWORD)
                .optionList(options.getInline())
                .body(packageName.getValue())
                .build().getInlineOptions();
    }

    @Override
    public List<String> getBlock() {
        return new GenericCommand.Builder(KEYWORD)
                .optionList(options.getBlock())
                .body(packageName.getValue())
                .build().getBlock();
    }

    @Override
    public String getInline() {
        return new GenericCommand.Builder(KEYWORD)
                .optionList(options.getInline())
                .body(packageName.getValue())
                .build().getInline();
    }

    @Override
    public String toString() {
        return "UsepackageCommand{" +
                "packageName=" + packageName +
                ", options='" + options + '\'' +
                '}';
    }
}
