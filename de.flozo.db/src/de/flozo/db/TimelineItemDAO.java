package de.flozo.db;

import de.flozo.common.dto.content.TimelineItem;

import java.util.List;

public interface TimelineItemDAO extends DAO<TimelineItem> {

    @Override
    TimelineItem get(int id);

    @Override
    TimelineItem get(String specifier);

    @Override
    List<TimelineItem> getAll();

    List<TimelineItem> getAllOfType(int id);

    List<TimelineItem> getAllOfType(String specifier);

    @Override
    void add(TimelineItem timelineItem);

    @Override
    void update(TimelineItem timelineItem);

    @Override
    void delete(TimelineItem timelineItem);
}
