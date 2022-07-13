package de.flozo.db;

import java.util.List;

public interface AddressDAO extends DAO<AddressDAOImpl> {

    @Override
    AddressDAOImpl get(int id);

    @Override
    AddressDAOImpl get(String specifier);

    @Override
    List<AddressDAOImpl> getAll();

    @Override
    int add(AddressDAOImpl addressDAO);

    @Override
    int update(AddressDAOImpl addressDAO);

    @Override
    int delete(AddressDAOImpl addressDAO);
}
