package de.flozo.db;

import de.flozo.common.dto.content.Enclosure;

import java.util.List;

public interface EnclosureDAO extends DAO<Enclosure> {

    @Override
    Enclosure get(int id);

    @Override
    Enclosure get(String specifier);

    @Override
    List<Enclosure> getAll();

    List<Enclosure> getAllIncluded();
    @Override
    void add(Enclosure enclosure);

    @Override
    void update(Enclosure enclosure);

    @Override
    void delete(Enclosure enclosure);
}
