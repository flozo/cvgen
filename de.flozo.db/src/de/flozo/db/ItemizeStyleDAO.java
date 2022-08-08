package de.flozo.db;

import de.flozo.common.dto.appearance.ItemizeStyle;

import java.util.List;

public interface ItemizeStyleDAO extends DAO<ItemizeStyle> {

    @Override
    ItemizeStyle get(int id);

    @Override
    ItemizeStyle get(String specifier);

    @Override
    List<ItemizeStyle> getAll();

    @Override
    void add(ItemizeStyle itemizeStyle);

    @Override
    void update(ItemizeStyle itemizeStyle);

    @Override
    void delete(ItemizeStyle itemizeStyle);
}
