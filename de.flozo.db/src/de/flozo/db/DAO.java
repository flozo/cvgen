package de.flozo.db;

import java.util.List;

public interface DAO<T> {

    T get(int id);
    T get(String specifier);
    List<T> getAll();
    int add(T t);
    int update(T t);
    int delete(T t);

}
