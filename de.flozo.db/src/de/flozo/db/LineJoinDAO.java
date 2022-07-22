package de.flozo.db;

import de.flozo.dto.appearance.LineJoin;

import java.util.List;

public interface LineJoinDAO extends ReadOnlyDAO<LineJoin> {

    @Override
    LineJoin get(int id);

    @Override
    LineJoin get(String specifier);

    @Override
    List<LineJoin> getAll();
}
