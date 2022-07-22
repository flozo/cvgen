package de.flozo.db;

import de.flozo.dto.appearance.PredefinedLineWidth;

import java.util.List;

public interface PredefinedLineWidthDAO extends ReadOnlyDAO<PredefinedLineWidth> {

    @Override
    PredefinedLineWidth get(int id);

    @Override
    PredefinedLineWidth get(String specifier);

    @Override
    List<PredefinedLineWidth> getAll();
}
