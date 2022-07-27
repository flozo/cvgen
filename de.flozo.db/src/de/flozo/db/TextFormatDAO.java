package de.flozo.db;

import de.flozo.common.dto.appearance.TextFormat;

import java.util.List;

public interface TextFormatDAO extends ReadOnlyDAO<TextFormat> {

    @Override
    TextFormat get(int id);

    @Override
    TextFormat get(String specifier);

    @Override
    List<TextFormat> getAll();
}
