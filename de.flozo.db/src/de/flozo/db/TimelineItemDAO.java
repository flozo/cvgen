package de.flozo.db;

import de.flozo.common.dto.content.TimelineItem;
import de.flozo.common.dto.content.TimelineTextItemLink;

import java.util.List;

public interface TimelineItemDAO extends DAO<TimelineItem> {

    int getCount();
    @Override
    TimelineItem get(int id);

    @Override
    TimelineItem get(String specifier);

    @Override
    List<TimelineItem> getAll();

    List<TimelineItem> getAllOfType(int id);

    List<TimelineItem> getAllOfType(String specifier);

    List<TimelineTextItemLink> getTextItems(int id);

    List<TimelineTextItemLink> getTextItems(String specifier);

    @Override
    void add(TimelineItem timelineItem);

    @Override
    void update(TimelineItem timelineItem);

    @Override
    void delete(TimelineItem timelineItem);
}
