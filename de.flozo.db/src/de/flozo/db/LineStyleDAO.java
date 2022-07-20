package de.flozo.db;

import de.flozo.common.appearance.LineStyle;

import java.util.List;

public interface LineStyleDAO extends DAO<LineStyle> {

    @Override
    LineStyle get(int id);

    @Override
    LineStyle get(String specifier);

    @Override
    List<LineStyle> getAll();

    @Override
    void add(LineStyle lineStyle);

    @Override
    void update(LineStyle lineStyle);

    @Override
    void delete(LineStyle lineStyle);
}
