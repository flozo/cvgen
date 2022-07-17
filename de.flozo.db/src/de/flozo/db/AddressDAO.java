package de.flozo.db;

import de.flozo.common.content.Address;

import java.util.List;

public interface AddressDAO extends DAO<Address> {

    @Override
    Address get(int id);

    @Override
    Address get(String specifier);

    @Override
    List<Address> getAll();

    @Override
    void add(Address addressDAO);

    @Override
    void update(Address addressDAO);

    @Override
    void delete(Address addressDAO);
}
