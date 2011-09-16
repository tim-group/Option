package com.timgroup.option.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.timgroup.functional.Function;
import com.timgroup.option.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

@SuppressWarnings("serial")
public class OptionArrayList<E> extends ArrayList<Option<E>> implements OptionList<E> {
    public OptionArrayList() {
        super();
    }

    public OptionArrayList(Collection<? extends Option<E>> options) {
        super(options);
    }

    public OptionArrayList(int i) {
        super(i);
    }

    @Override
    public <M> OptionList<M> map(final Function<E, M> function) {
        return newOptionArrayList(transform(this, new com.google.common.base.Function<Option<E>, Option<M>>() {
            @Override
            public Option<M> apply(Option<E> from) {
                return from.map(function);
            }
        }));
    }

    @Override
    public <M> OptionList<M> flatMap(final Function<E, Option<M>> function) {
        return newOptionArrayList(transform(this, new com.google.common.base.Function<Option<E>, Option<M>>() {
            @Override
            public Option<M> apply(Option<E> from) {
                return from.flatMap(function);
            }
        }));
    }

    @Override
    public List<E> flatten() {
        Collection<Option<E>> definedElements = Collections2.filter(this, OptionArrayList.<E>isDefined());
        return newArrayList(transform(definedElements, OptionArrayList.<E>unwrapOption()));
    }

    private static <T> com.google.common.base.Function<Option<T>, T> unwrapOption() {
        return new com.google.common.base.Function<Option<T>, T>() {
            @Override public T apply(Option<T> option) {
                return option.get();
            }
        };
    }

    private static <T> Predicate<Option<T>> isDefined() {
        return new Predicate<Option<T>>() {
            @Override public boolean apply(Option<T> option) {
                return option.isDefined();
            }
        };
    }

    public static <E> OptionArrayList<E> newOptionArrayList(Iterable<? extends Option<E>> elements) {
        checkNotNull(elements);

        if (elements instanceof Collection) {
            return new OptionArrayList<E>((Collection<? extends Option<E>>) elements);
        } else {
            return newOptionArrayList(elements.iterator());
        }
    }

    public static <E> OptionArrayList<E> newOptionArrayList(Iterator<? extends Option<E>> elements) {
        checkNotNull(elements);
        OptionArrayList<E> list = newOptionArrayList();
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list;
    }

    public static <E> OptionArrayList<E> newOptionArrayList() {
        return new OptionArrayList<E>();
    }

    public static <E> OptionArrayList<E> newOptionArrayList(Option<E>... elements) {
        return newOptionArrayList(asList(elements));
    }
}
