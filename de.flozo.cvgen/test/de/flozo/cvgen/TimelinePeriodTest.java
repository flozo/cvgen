package de.flozo.cvgen;

import de.flozo.common.dto.content.TimelineItem;
import de.flozo.latex.core.Delimiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimelinePeriodTest {

    TimelineItem timelineItem1 = new TimelineItem(0, "", 1, 2012, 12, 2023, "", "", "", "", null, false);
    TimelineItem timelineItemEndOnly = new TimelineItem(0, "", 0, 0, 8, 2015, "", "", "", "", null, false);
    TimelineItem timelineItemStartOnly = new TimelineItem(0, "", 3, 2011, 0, 0, "", "", "", "", null, false);

    @Test
    void getPeriodTag_defaults() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withDefaultFormat(timelineItem1);
        assertEquals("01/2012\\,--\\,12/2023", timelinePeriod.getPeriodTag());
    }

    @Test
    void getPeriodTag_custom() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withCustomFormat(timelineItem1, Delimiter.COMMA, Delimiter.EM_DASH, false);
        assertEquals("1,2012\\,---\\,12,2023", timelinePeriod.getPeriodTag());
    }

    @Test
    void getPeriodTag_end_date_only() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withDefaultFormat(timelineItemEndOnly);
        assertEquals("08/2015", timelinePeriod.getPeriodTag());
    }

    @Test
    void getPeriodTag_start_date_only() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withDefaultFormat(timelineItemStartOnly);
        assertEquals("03/2011", timelinePeriod.getPeriodTag());
    }


}
