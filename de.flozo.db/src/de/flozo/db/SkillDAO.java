package de.flozo.db;

import de.flozo.common.dto.content.Skill;

import java.util.List;

public interface SkillDAO extends DAO<Skill> {

    @Override
    Skill get(int id);

    @Override
    Skill get(String specifier);

    @Override
    List<Skill> getAll();

    @Override
    void add(Skill skill);

    @Override
    void update(Skill skill);

    @Override
    void delete(Skill skill);
}
