package org.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    public static LocalDateTime getCurrentDt() {
        return LocalDateTime.now();
    }

    public static Timestamp getCurrentTs() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp dtToTs(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime tsToDt(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

}
