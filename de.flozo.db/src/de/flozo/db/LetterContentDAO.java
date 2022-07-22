package de.flozo.db;

import de.flozo.dto.content.LetterContent;

import java.util.List;

public interface LetterContentDAO extends DAO<LetterContent> {

    @Override
    LetterContent get(int id);

    @Override
    LetterContent get(String specifier);

    @Override
    List<LetterContent> getAll();

    @Override
    void add(LetterContent letterContent);

    @Override
    void update(LetterContent letterContent);

    @Override
    void delete(LetterContent letterContent);
}
