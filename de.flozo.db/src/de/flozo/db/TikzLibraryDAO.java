package de.flozo.db;

import de.flozo.dto.latex.TikzLibrary;

import java.util.List;

public interface TikzLibraryDAO extends DAO<TikzLibrary> {

    @Override
    TikzLibrary get(int id);

    @Override
    TikzLibrary get(String specifier);

    @Override
    List<TikzLibrary> getAll();

    @Override
    void add(TikzLibrary tikzLibrary);

    @Override
    void update(TikzLibrary tikzLibrary);

    @Override
    void delete(TikzLibrary tikzLibrary);
}
