package de.flozo.db;

import de.flozo.dto.appearance.TextStyle;

import java.util.List;

public interface TextStyleDAO extends DAO<TextStyle> {

    @Override
    TextStyle get(int id);

    @Override
    TextStyle get(String specifier);

    @Override
    List<TextStyle> getAll();

    @Override
    void add(TextStyle textStyle);

    @Override
    void update(TextStyle textStyle);

    @Override
    void delete(TextStyle textStyle);
}
