package com.timgroup.option.util;

import org.junit.Test;

import static com.timgroup.option.None.None;
import static com.timgroup.option.Some.Some;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OptionMapTest {
    @Test public void
    get_returns_some_for_values_contained_in_the_map_and_none_for_values_not_contained_in_the_map() {
       OptionMap<String, String> map = new OptionMap();

       map.put("foo", "bar");

       assertThat(map.get("foo"), is(Some("bar")));
       assertThat(map.get("foo2"), is(None(String.class)));
    }
}
