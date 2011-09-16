package com.timgroup.option.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OptionMapTest {
    @Test public void
    optionMapTest() {
       OptionMap<String, String> map = new OptionMap<String, String>();

       map.put("foo", "bar");

       assertEquals(map.get("foo").get(), "bar");
       assertEquals(map.get("foo2").getOrElse("else"), "else");

    }
}
