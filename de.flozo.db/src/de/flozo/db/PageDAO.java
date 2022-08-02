package de.flozo.db;

import de.flozo.common.dto.appearance.Page;

import java.util.List;

public interface PageDAO extends DAO<Page> {

    @Override
    Page get(int id);

    @Override
    Page get(String specifier);

    @Override
    List<Page> getAll();

    @Override
    void add(Page page);

    @Override
    void update(Page page);

    @Override
    void delete(Page page);
}
