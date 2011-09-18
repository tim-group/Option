package com.timgroup.option;

import com.timgroup.functional.Function;
import com.timgroup.functional.Function0;
import com.timgroup.functional.Predicate;
import com.timgroup.functional.Unit;
import org.junit.Test;

import java.util.Iterator;

import static ch.lambdaj.Lambda.on;
import static com.timgroup.option.None.None;
import static com.timgroup.option.Some.Some;
import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SomeTest {

    private final AClass instance = new AClass();
    private final Integer object = valueOf(1);
    private final Integer anotherObject = valueOf(2);

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateSomeWithNull() {
        Some(null);
    }

    @Test
    public void isDefined() {
        assertThat(Some(object).isDefined(), is(true));
    }

    @Test
    public void get() {
        assertThat(Some(object).get(), is(object));
    }

    @Test
    public void getOrElse() {
        assertThat(Some(object).getOrElse(anotherObject), is(object));
    }

    @Test
    public void getOrElseClosure() {
        assertThat(Some(object).getOrElse(new Function0<Integer>() {
            @Override public Integer apply() {
                return anotherObject;
            }
        }), is(object));
    }

    @Test
    public void toList() {
        assertThat(Some(object).toList(), is(asList(object)));
    }

    @Test
    public void toSet() {
        assertThat(Some(object).toSet(), is(singleton(object)));
    }

    @Test
    public void doWith() {
        AClass mockedInstance = mock(AClass.class);

        given(mockedInstance.toString()).willReturn("Bla");
        Some(mockedInstance).execute(new Unit<AClass>() {
            @Override
            public void execute(AClass input) {
                input.aMethod();
            }
        });
        verify(mockedInstance).aMethod();
    }

    @Test
    public void filterInclude() {
        assertThat(Some(object).filter(new Predicate<Integer>() {
            @Override public Boolean apply(Integer input) {
                return true;
            }
        }), is(Some(object)));
    }

    @Test
    public void filterExclude() {
        assertThat(Some(object).filter(new Predicate<Integer>() {
            @Override public Boolean apply(Integer input) {
                return false;
            }
        }), is(None(Integer.class)));
    }

    @Test
    public void map() {
        assertThat(Some(object).map(new Function<Integer, String>() {
            @Override public String apply(Integer from) {
                return from.toString();
            }
        }), is(Some("1")));
    }

    @Test
    public void flatMapToSome() {
        assertThat(Some(object).flatMap(new Function<Integer, Option<String>>() {
            @Override public Option<String> apply(Integer from) {
                return Some(from.toString());
            }
        }), is(Some("1")));
    }

    @Test
    public void flatMapToNone() {
        assertThat(Some(object).flatMap(new Function<Integer, Option<String>>() {
            @Override public Option<String> apply(Integer from) {
                return None(String.class);
            }
        }), is(None(String.class)));
    }

    @Test
    public void iterator() {
        Iterator<Integer> iterator = Some(object).iterator();
        assertTrue(iterator.hasNext());
        assertThat(iterator.next(), is(object));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void orElse() {
        assertThat(Some(object).orElse(Some(anotherObject)), is(Some(object)));
    }

    @Test
    public void orElseClosure() {
        assertThat(Some(object).orElse(new Function0<Option<Integer>>() {
            @Override public Option<Integer> apply() {
                return Some(anotherObject);
            }
        }), is(Some(object)));
    }

    @Test
    public void lambdaMap() {
        assertThat(Some(instance).map(on(AClass.class).toString()), is(Some("1")));
    }

    @Test
    public void lambdaFlatMap() {
        assertThat(Some(instance).flatMap(on(AClass.class).aMethodThatReturnsAnOption()), is(Some(3)));
    }

    private static class AClass {
        private final int value = 1;

        public int value() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public void aMethod() {
            // Nothing
        }

        public Option<Integer> aMethodThatReturnsAnOption() {
            return Some(value + 2);
        }

        public boolean falseMethod() {
            return false;
        }

        public boolean trueMethod() {
            return true;
        }
    }
}
