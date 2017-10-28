package com.redlongcitywork.gasshop.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 
 * 28/10/2017
 * class for operations with time
 */
@Service
public class TimeUtils {

    public Timestamp getCurrentTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        return time;
    }
}
