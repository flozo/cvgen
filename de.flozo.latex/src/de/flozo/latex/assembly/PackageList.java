package de.flozo.latex.assembly;

import de.flozo.common.dto.latex.LatexPackage;
import de.flozo.latex.core.ExpressionList;
import de.flozo.latex.core.FormattedExpressionList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PackageList {

    private final List<LatexPackage> packages;

    public PackageList(List<LatexPackage> packages) {
        this.packages = packages;
    }

    public Map<LatexPackage, ExpressionList> getOptionMap() {
        Map<LatexPackage, ExpressionList> optionMap = new LinkedHashMap<>();
        for (LatexPackage latexPackage : packages) {
            ExpressionList options;
            if (latexPackage.getOptions() != null) {
                options = new FormattedExpressionList.Builder(latexPackage.getOptions()).build();
            } else {
                options = new FormattedExpressionList.Builder("").build();
            }
            optionMap.put(latexPackage, options);
        }
        return optionMap;
    }


    @Override
    public String toString() {
        return "PackageList{" +
                "packages=" + packages +
                '}';
    }
}
