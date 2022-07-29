package de.flozo.db;

import de.flozo.common.dto.appearance.Element;

import java.util.List;

public interface ElementDAO extends DAO<Element> {

    @Override
    Element get(int id);

    @Override
    Element get(String specifier);

    @Override
    List<Element> getAll();

    List<Element> getAllIncluded();
    @Override
    void add(Element element);

    @Override
    void update(Element element);

    @Override
    void delete(Element element);
}
