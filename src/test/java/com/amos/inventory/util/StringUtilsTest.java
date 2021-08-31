package com.amos.inventory.util;

import org.junit.Test;

/**
 * @author amos
 * @since 2021/8/30 0030 22:44
 */
public class StringUtilsTest
{

    @Test
    public void isEmpty()
    {
        String s="";
        boolean empty = StringUtils.isEmpty(s);
        assert empty==true;
    }

    @Test
    public void isNotEmpty()
    {
        String s="123";
        boolean notEmpty = StringUtils.isNotEmpty(s);
        assert notEmpty==true;
    }
}
