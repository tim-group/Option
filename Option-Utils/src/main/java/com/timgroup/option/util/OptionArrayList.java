package com.timgroup.option.util;

import com.timgroup.functional.Function;
import com.timgroup.option.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
        return mapInternal(new Function<Option<E>, Option<M>>() {
            @Override public Option<M> apply(Option<E> from) {
                return from.map(function);
            }
        });
    }

    @Override
    public <M> OptionList<M> flatMap(final Function<E, Option<M>> function) {
        return mapInternal(new Function<Option<E>, Option<M>>() {
            @Override public Option<M> apply(Option<E> from) {
                return from.flatMap(function);
            }
        });
    }

    @Override
    public List<E> flatten() {
        final OptionArrayList result = newOptionArrayList();
        for (Option<E> element : this) {
            if (element.isDefined()) {
                result.add(element.get());
            }
        }
        return result;
    }

    private <M> OptionList<M> mapInternal(final Function<Option<E>, Option<M>> function) {
        final OptionArrayList<M> result = newOptionArrayList();
        for (Option<E> element : this) {
            result.add(function.apply(element));
        }
        return result;
    }

    public static <E> OptionArrayList<E> newOptionArrayList() {
        return new OptionArrayList<E>();
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

    public static <E> OptionArrayList<E> newOptionArrayList(Option<E>... elements) {
        return newOptionArrayList(asList(elements));
    }

    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
