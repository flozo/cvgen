package de.flozo.db;

import de.flozo.common.appearance.LineCap;

import java.util.List;

public interface LineCapDAO extends ReadOnlyDAO<LineCap> {

    @Override
    LineCap get(int id);

    @Override
    LineCap get(String specifier);

    @Override
    List<LineCap> getAll();


}
