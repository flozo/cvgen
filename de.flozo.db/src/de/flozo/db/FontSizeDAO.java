package de.flozo.db;

import de.flozo.common.appearance.FontSize;

import java.util.List;

public interface FontSizeDAO extends ReadOnlyDAO<FontSize> {

    @Override
    FontSize get(int id);

    @Override
    FontSize get(String specifier);

    @Override
    List<FontSize> getAll();
}
