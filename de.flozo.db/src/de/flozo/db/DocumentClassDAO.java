package de.flozo.db;

import de.flozo.common.dto.latex.DocumentClass;

import java.util.List;

public interface DocumentClassDAO extends DAO<DocumentClass> {

    @Override
    DocumentClass get(int id);

    @Override
    DocumentClass get(String specifier);

    @Override
    List<DocumentClass> getAll();

    @Override
    void add(DocumentClass documentClass);

    @Override
    void update(DocumentClass documentClass);

    @Override
    void delete(DocumentClass documentClass);

    List<DocumentClass> getAllIncluded();
}
