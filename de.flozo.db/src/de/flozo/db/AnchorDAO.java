package de.flozo.db;

import de.flozo.dto.appearance.Anchor;

import java.util.List;

public interface AnchorDAO extends ReadOnlyDAO<Anchor> {

    @Override
    Anchor get(int id);

    @Override
    Anchor get(String specifier);

    @Override
    List<Anchor> getAll();


}
