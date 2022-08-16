package de.flozo.db;

import de.flozo.common.dto.appearance.Rectangle;

import java.util.List;

public interface RectangleDAO extends DAO<Rectangle> {

    @Override
    Rectangle get(int id);

    @Override
    Rectangle get(String specifier);

    @Override
    List<Rectangle> getAll();

    List<Rectangle> getAllIncluded();

    @Override
    void add(Rectangle rectangle);

    @Override
    void update(Rectangle rectangle);

    @Override
    void delete(Rectangle rectangle);
}
