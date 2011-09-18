package com.timgroup.option;

import com.timgroup.functional.Function;
import com.timgroup.functional.Function0;
import com.timgroup.functional.Predicate;
import com.timgroup.functional.Unit;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static ch.lambdaj.Lambda.on;
import static com.timgroup.option.None.None;
import static com.timgroup.option.Some.Some;
import static java.lang.Integer.valueOf;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class NoneTest {

    private final Integer anotherObject = valueOf(2);

    @Test
    public void isDefined() {
        assertThat(None().isDefined(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void get() {
        None().get();
    }

    @Test
    public void getOrElse() {
        assertThat(None(Integer.class).getOrElse(anotherObject), is(anotherObject));
    }

    @Test
    public void getOrElseClosure() {
        assertThat(None(Integer.class).getOrElse(new Function0<Integer>() {
            @Override public Integer apply() {
                return anotherObject;
            }
        }), is(anotherObject));
    }

    @Test
    public void toList() {
        assertThat(None().toList(), is(emptyList()));
    }

    @Test
    public void toSet() {
        assertThat(None().toSet(), is(emptySet()));
    }

    @Test
    public void doWith() {
        None(AClass.class).execute(new Unit<AClass>() {
            @Override public void execute(AClass input) {
                input.aMethod();
            }
        });
    }

    @Test
    public void filterInclude() {
        assertThat(None(Integer.class).filter(new Predicate<Integer>() {
            @Override public Boolean apply(Integer input) {
                return true;
            }
        }), is(None(Integer.class)));
    }

    @Test
    public void filterExclude() {
        assertThat(None(Integer.class).filter(new Predicate<Integer>() {
            @Override public Boolean apply(Integer input) {
                return false;
            }
        }), is(None(Integer.class)));
    }

    @Test
    public void map() {
        assertThat(None(Integer.class).map(new Function<Integer, String>() {
            @Override public String apply(Integer from) {
                return from.toString();
            }
        }), is(None(String.class)));
    }

    @Test
    public void flatMap() {
        assertThat(None(Integer.class).flatMap(new Function<Integer, Option<String>>() {
            @Override public Option<String> apply(Integer from) {
                return Some(from.toString());
            }
        }), is(None(String.class)));
    }

    @Test
    public void iterator() {
        Iterator<Integer> iterator = None(Integer.class).iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void orElse() {
        assertThat(None(Integer.class).orElse(Some(anotherObject)), is(Some(anotherObject)));
    }

    @Test
    public void orElseClosure() {
        assertThat(None(Integer.class).orElse(new Function0<Option<Integer>>() {
            @Override public Option<Integer> apply() {
                return Some(anotherObject);
            }
        }), is(Some(anotherObject)));
    }

    @Test
    public void lambdaMap() {
        assertThat(None(Integer.class).map(on(AClass.class).toString()), is(None(String.class)));
    }

    @Test
    public void lambdaFlatMap() {
        assertThat(None(Integer.class).flatMap(on(AClass.class).aMethodThatReturnsAnOption()), is(None(Integer.class)));
    }

    public static class AClass {
        private final int value = 1;

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public void aMethod() {
            // Nothing
        }

        public boolean falseMethod() {
            return false;
        }

        public boolean trueMethod() {
            return true;
        }

        public Option<Integer> aMethodThatReturnsAnOption() {
            return Some(value + 2);
        }
    }
}
