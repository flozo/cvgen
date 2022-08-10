package de.flozo.db;

import de.flozo.common.dto.content.TextItem;

import java.util.List;

public interface TextItemDAO extends DAO<TextItem> {

    @Override
    TextItem get(int id);

    @Override
    TextItem get(String specifier);

    @Override
    List<TextItem> getAll();

    @Override
    void add(TextItem textItem);

    @Override
    void update(TextItem textItem);

    @Override
    void delete(TextItem textItem);
}
