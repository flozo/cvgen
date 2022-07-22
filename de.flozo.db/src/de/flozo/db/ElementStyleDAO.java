package de.flozo.db;

import de.flozo.dto.appearance.ElementStyle;

import java.util.List;

public interface ElementStyleDAO extends DAO<ElementStyle> {

    @Override
    ElementStyle get(int id);

    @Override
    ElementStyle get(String specifier);

    @Override
    List<ElementStyle> getAll();

    @Override
    void add(ElementStyle elementStyle);

    @Override
    void update(ElementStyle elementStyle);

    @Override
    void delete(ElementStyle elementStyle);
}
