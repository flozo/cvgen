package de.flozo.db;

import de.flozo.common.dto.appearance.SeparationSpace;

import java.util.List;

public interface SeparationSpaceDAO extends DAO<SeparationSpace> {

    @Override
    SeparationSpace get(int id);

    @Override
    SeparationSpace get(String specifier);

    @Override
    List<SeparationSpace> getAll();

    @Override
    void add(SeparationSpace separationSpace);

    @Override
    void update(SeparationSpace separationSpace);

    @Override
    void delete(SeparationSpace separationSpace);
}
