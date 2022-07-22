package de.flozo.db;

import de.flozo.dto.appearance.LineWidth;

import java.util.List;

public interface LineWidthDAO extends DAO<LineWidth> {

    @Override
    LineWidth get(int id);

    @Override
    LineWidth get(String specifier);

    @Override
    List<LineWidth> getAll();

    @Override
    void add(LineWidth lineWidth);

    @Override
    void update(LineWidth lineWidth);

    @Override
    void delete(LineWidth lineWidth);
}
