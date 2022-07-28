package de.flozo.db;

import de.flozo.common.dto.appearance.Layer;

import java.util.List;

public interface LayerDAO extends DAO<Layer> {

    @Override
    Layer get(int id);

    @Override
    Layer get(String specifier);

    @Override
    List<Layer> getAll();

    @Override
    void add(Layer layer);

    @Override
    void update(Layer layer);

    @Override
    void delete(Layer layer);
}
