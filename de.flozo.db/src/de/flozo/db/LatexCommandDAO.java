package de.flozo.db;

import de.flozo.dto.LatexCommand;

import java.util.List;

public interface LatexCommandDAO extends ReadOnlyDAO<LatexCommand> {

    @Override
    LatexCommand get(int id);

    @Override
    LatexCommand get(String specifier);

    @Override
    List<LatexCommand> getAll();
}
