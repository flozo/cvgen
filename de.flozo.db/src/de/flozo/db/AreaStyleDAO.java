package de.flozo.db;

import de.flozo.dto.appearance.AreaStyle;

import java.util.List;

public interface AreaStyleDAO extends DAO<AreaStyle> {

    @Override
    AreaStyle get(int id);

    @Override
    AreaStyle get(String specifier);

    @Override
    List<AreaStyle> getAll();

    @Override
    void add(AreaStyle areaStyle);

    @Override
    void update(AreaStyle areaStyle);

    @Override
    void delete(AreaStyle areaStyle);
}
