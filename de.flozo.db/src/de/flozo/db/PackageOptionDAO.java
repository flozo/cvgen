package de.flozo.db;

import de.flozo.common.dto.latex.PackageOption;

import java.util.List;

public interface PackageOptionDAO extends DAO<PackageOption> {

    @Override
    PackageOption get(int id);

    @Override
    PackageOption get(String specifier);

    @Override
    List<PackageOption> getAll();

    @Override
    void add(PackageOption packageOption);

    @Override
    void update(PackageOption packageOption);

    @Override
    void delete(PackageOption packageOption);
}
