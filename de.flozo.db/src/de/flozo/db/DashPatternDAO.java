package de.flozo.db;

import de.flozo.common.appearance.DashPattern;

import java.util.List;

public interface DashPatternDAO extends ReadOnlyDAO<DashPattern> {

    @Override
    DashPattern get(int id);

    @Override
    DashPattern get(String specifier);

    @Override
    List<DashPattern> getAll();
}
