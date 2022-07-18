package de.flozo.db;

import java.util.List;

public interface DAO<T> {

    T get(int id);
    T get(String specifier);
    List<T> getAll();
    void add(T t);
    void update(T t);
    void delete(T t);

}
