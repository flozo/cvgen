package de.flozo.latex.assembly;

import de.flozo.common.dto.latex.LatexPackage;
import de.flozo.common.dto.latex.PackageOption;

import java.util.List;
import java.util.stream.Collectors;

public class PackageOptionList {

    private final List<PackageOption> packageOptions;

    public PackageOptionList(List<PackageOption> packageOptions) {
        this.packageOptions = packageOptions;
    }

    public List<PackageOption> getIncluded() {
        return packageOptions.stream().filter(PackageOption::isInclude).collect(Collectors.toList());
    }

    public List<LatexPackage> getPackages() {
        return packageOptions.stream()
                .map(PackageOption::getLatexPackage)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "PackageOptionList{" +
                "packageOptions=" + packageOptions +
                '}';
    }
}
