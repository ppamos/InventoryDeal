package com.amos.inventory.util;


import org.junit.Test;

/**
 * @author amos
 * @since 2021/8/22 13:11
 */
public class AssertTest {

    @Test
   public void testNoNull() {
        Object o=null;
        Assert.noNull(o);
    }

    @Test
    public void testNoNull2() {
        Object o=null;
        Assert.noNull(o,"hello assert");
    }
}