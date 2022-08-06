package de.flozo.db;

import de.flozo.common.dto.appearance.Icon;

import java.util.List;

public interface IconDAO extends DAO<Icon> {

    @Override
    Icon get(int id);

    @Override
    Icon get(String specifier);

    @Override
    List<Icon> getAll();

    @Override
    void add(Icon icon);

    @Override
    void update(Icon icon);

    @Override
    void delete(Icon icon);
}
