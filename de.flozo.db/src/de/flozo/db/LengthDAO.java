package de.flozo.db;

import de.flozo.common.appearance.Length;

import java.util.List;

public interface LengthDAO extends DAO<Length> {

    @Override
    Length get(int id);

    @Override
    Length get(String specifier);

    @Override
    List<Length> getAll();

    @Override
    void add(Length length);

    @Override
    void update(Length length);

    @Override
    void delete(Length length);
}
