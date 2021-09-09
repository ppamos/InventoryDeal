package com.amos.inventory.util;

import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils
{
    public static Date getNow()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}
