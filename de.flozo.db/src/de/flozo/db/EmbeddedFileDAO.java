package de.flozo.db;

import de.flozo.common.dto.content.EmbeddedFile;

import java.util.List;

public interface EmbeddedFileDAO extends DAO<EmbeddedFile> {

    @Override
    EmbeddedFile get(int id);

    @Override
    EmbeddedFile get(String specifier);

    @Override
    List<EmbeddedFile> getAll();

    List<EmbeddedFile> getAllIncluded();
    @Override
    void add(EmbeddedFile embeddedFile);

    @Override
    void update(EmbeddedFile embeddedFile);

    @Override
    void delete(EmbeddedFile embeddedFile);
}
