package logs;

import java.time.ZonedDateTime;

import static core.utilities.DateTimeInfo.DEFAULT_FORMATTER;
import static core.utilities.DateTimeInfo.ZONE_ID;


public abstract class AbstractLogWriter {

    final boolean saveLogs;

    AbstractLogWriter(boolean save) { saveLogs = save; }

    public abstract void writeLogs(String msg);

    String addDate(String msg) {
        return String.format("[%s]: %s",
                ZonedDateTime
                        .now(ZONE_ID)
                        .format(DEFAULT_FORMATTER), msg);
    }
}
