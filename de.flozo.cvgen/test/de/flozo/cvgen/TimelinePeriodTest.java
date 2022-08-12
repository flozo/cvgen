package de.flozo.cvgen;

import de.flozo.common.dto.content.TimelineItem;
import de.flozo.latex.core.Delimiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimelinePeriodTest {

    TimelineItem timelineItem = new TimelineItem(0, "", 1, 2012, 12, 2023, "", "", "", "", null);

    @Test
    void assemblePeriodTag_defaults() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withDefaultFormat(timelineItem);
        assertEquals("01/2012--12/2023", timelinePeriod.assemblePeriodTag());
    }

    @Test
    void assemblePeriodTag_custom() {
        TimelinePeriod timelinePeriod = TimelinePeriod.withCustomFormat(timelineItem, Delimiter.COMMA, Delimiter.EM_DASH, false);
        assertEquals("1,2012---12,2023", timelinePeriod.assemblePeriodTag());
    }

}
