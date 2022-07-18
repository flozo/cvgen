package de.flozo.db;

import java.util.List;

public interface ReadOnlyDAO<T> {

    T get(int id);
    T get(String specifier);
    List<T> getAll();

}
