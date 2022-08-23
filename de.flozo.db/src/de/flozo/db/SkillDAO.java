package de.flozo.db;

import de.flozo.common.dto.content.Skill;

import java.util.List;
import java.util.Map;

public interface SkillDAO extends DAO<Skill> {

    @Override
    Skill get(int id);

    @Override
    Skill get(String specifier);

    @Override
    List<Skill> getAll();

    List<Skill> getAllByType(String specifier);
    Map<String, String> getMapByType(String specifier);

    @Override
    void add(Skill skill);

    @Override
    void update(Skill skill);

    @Override
    void delete(Skill skill);
}
