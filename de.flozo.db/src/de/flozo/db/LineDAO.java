package de.flozo.db;

import de.flozo.common.dto.appearance.Line;

import java.util.List;

public interface LineDAO extends DAO<Line> {

    @Override
    Line get(int id);

    @Override
    Line get(String specifier);

    @Override
    List<Line> getAll();

    @Override
    void add(Line line);

    @Override
    void update(Line line);

    @Override
    void delete(Line line);
}
