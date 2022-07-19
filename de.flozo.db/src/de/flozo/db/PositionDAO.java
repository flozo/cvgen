package de.flozo.db;

import de.flozo.common.appearance.Position;

import java.util.List;

public interface PositionDAO extends DAO<Position> {

    @Override
    Position get(int id);

    @Override
    Position get(String specifier);

    @Override
    List<Position> getAll();

    @Override
    void add(Position position);

    @Override
    void update(Position position);

    @Override
    void delete(Position position);
}
