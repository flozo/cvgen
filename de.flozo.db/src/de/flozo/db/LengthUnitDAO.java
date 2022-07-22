package de.flozo.db;

import de.flozo.dto.appearance.LengthUnit;

import java.util.List;

public interface LengthUnitDAO extends ReadOnlyDAO<LengthUnit> {

    @Override
    LengthUnit get(int id);

    @Override
    LengthUnit get(String specifier);

    @Override
    List<LengthUnit> getAll();
}
