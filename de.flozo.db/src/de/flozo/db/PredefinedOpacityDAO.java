package de.flozo.db;

import de.flozo.common.dto.appearance.PredefinedOpacity;

import java.util.List;

public interface PredefinedOpacityDAO extends ReadOnlyDAO<PredefinedOpacity> {

    @Override
    PredefinedOpacity get(int id);

    @Override
    PredefinedOpacity get(String specifier);

    @Override
    List<PredefinedOpacity> getAll();
}
