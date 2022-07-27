package de.flozo.db;

import de.flozo.common.dto.latex.LatexPackage;

import java.util.List;

public interface LatexPackageDAO extends ReadOnlyDAO<LatexPackage> {

    @Override
    LatexPackage get(int id);

    @Override
    LatexPackage get(String specifier);

    @Override
    List<LatexPackage> getAll();

    List<LatexPackage> getAllIncluded();
}
