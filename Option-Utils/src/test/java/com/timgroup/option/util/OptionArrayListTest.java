package com.timgroup.option.util;

import com.timgroup.functional.Function;
import com.timgroup.option.Option;
import org.junit.Test;

import java.util.List;

import static com.timgroup.option.None.None;
import static com.timgroup.option.Some.Some;
import static com.timgroup.option.util.OptionArrayList.newOptionArrayList;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class OptionArrayListTest {
    @SuppressWarnings("unchecked")
    @Test public void
    testMap() {
        OptionArrayList<String> list = newOptionArrayList(newOptionArrayList(Some(1), None(Integer.class)).map(intToString()));
        assertThat(list, is(newOptionArrayList(Some("1"), None(String.class))));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    testFlatMap() {
        OptionArrayList<String> list = newOptionArrayList(newOptionArrayList(Some(1), None(Integer.class)).flatMap(intToSomeString()));
        assertThat(list, is(newOptionArrayList(Some("1"), None(String.class))));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    testFlatten() {
        List<Integer> list = newOptionArrayList(Some(1), None(Integer.class)).flatten();
        assertThat(list, is(asList(1)));
    }

    private static Function<Integer, String> intToString() {
        return new Function<Integer, String>() {
            @Override public String apply(Integer from) {
                return from.toString();
            }
        };
    }

    private static Function<Integer, Option<String>> intToSomeString() {
        return new Function<Integer, Option<String>>() {
            @Override public Option<String> apply(Integer from) {
                return Some(from.toString());
            }
        };
    }
}
