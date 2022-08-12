package de.flozo.cvgen;

import de.flozo.common.dto.content.TimelineItem;
import de.flozo.latex.core.Delimiter;

public class TimelinePeriod {

    public static final Delimiter DEFAULT_MONTH_YEAR_DELIMITER = Delimiter.SLASH;
    public static final Delimiter DEFAULT_DATE_DELIMITER = Delimiter.EN_DASH;
    public static final boolean DEFAULT_LEADING_ZERO = true;

    private final TimelineItem timelineItem;
    private final Delimiter monthYearDelimiter;
    private final Delimiter dateDelimiter;
    private final boolean leadingZero;

    private TimelinePeriod(TimelineItem timelineItem, Delimiter monthYearDelimiter, Delimiter dateDelimiter, boolean leadingZero) {
        this.timelineItem = timelineItem;
        this.monthYearDelimiter = monthYearDelimiter;
        this.dateDelimiter = dateDelimiter;
        this.leadingZero = leadingZero;
    }

    public static TimelinePeriod withCustomFormat(TimelineItem timelineItem, Delimiter monthYearDelimiter, Delimiter dateDelimiter, boolean leadingZero) {
        return new TimelinePeriod(timelineItem, monthYearDelimiter, dateDelimiter, leadingZero);
    }

    public static TimelinePeriod withDefaultFormat(TimelineItem timelineItem) {
        return withCustomFormat(timelineItem, DEFAULT_MONTH_YEAR_DELIMITER, DEFAULT_DATE_DELIMITER, DEFAULT_LEADING_ZERO);
    }

    private String formattedNumber(int number) {
        return String.format("%02d", number);
    }

    private String assembleDate(int month, int year) {
        return (leadingZero ? formattedNumber(month) : month) + monthYearDelimiter.getString() + year;
    }

    public String assemblePeriodTag() {
        StringBuilder periodTag = new StringBuilder();
        if (timelineItem.getPeriodStartYear() != 0) {
            periodTag.append(assembleDate(timelineItem.getPeriodStartMonth(), timelineItem.getPeriodStartYear()));
        }
        if (timelineItem.getPeriodStartYear() != 0 && timelineItem.getPeriodEndYear() != 0) {
            periodTag.append(dateDelimiter.getString());
        }
        if (timelineItem.getPeriodEndYear() != 0) {
            periodTag.append(assembleDate(timelineItem.getPeriodEndMonth(), timelineItem.getPeriodEndYear()));
        }
        return periodTag.toString();
    }

    @Override
    public String toString() {
        return "TimelinePeriod{" +
                "timelineItem=" + timelineItem +
                ", monthYearDelimiter=" + monthYearDelimiter +
                ", dateDelimiter=" + dateDelimiter +
                ", leadingZero=" + leadingZero +
                '}';
    }
}
