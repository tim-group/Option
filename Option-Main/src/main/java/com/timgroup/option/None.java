package com.timgroup.option;

import com.timgroup.functional.Function;
import com.timgroup.functional.Function0;
import com.timgroup.functional.Unit;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

public class None<T> extends Option<T> {
    private static final int NONE_HASH_CODE = 12345;

    @SuppressWarnings("rawtypes")
    private static final Option<?> None = new None();

    @SuppressWarnings("unchecked")
    public static <M> Option<M> None() {
        return (None<M>) None;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public static <M> Option<M> None(Class<M> clas) {
        return (None<M>) None;
    }

    private None() {
        // Empty
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get() {
        throw new NoSuchElementException("Trying to call get() on None.");
    }

    @Override
    public T getOrElse(T alternative) {
        return alternative;
    }

    @Override
    public T getOrElse(Function0<T> alternative) {
        return alternative.apply();
    }

    @Override
    public <M> Option<M> map(Function<T, M> function) {
        return None();
    }

    @Override
    public <M> Option<M> map(M lambdaArgument) {
        return None();
    }

    @Override
    public <M> Option<M> flatMap(Function<T, Option<M>> function) {
        return None();
    }

    @Override
    public <M> Option<M> flatMap(Option<M> lambdaArgument) {
        return None();
    }

    @Override
    public Option<T> filter(Function<T, Boolean> predicate) {
        return None();
    }

    @Override
    public Set<T> toSet() {
        return emptySet();
    }

    @Override
    public Option<T> orElse(Option<T> alternative) {
        return alternative;
    }

    @Override
    public Option<T> orElse(Function0<Option<T>> alternative) {
        return alternative.apply();
    }

    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }

    @Override
    public List<T> toList() {
        return emptyList();
    }

    @Override
    public void execute(Unit<T> unit) {
        // Nothing
    }

    @Override
    public int hashCode() {
        return NONE_HASH_CODE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == None.class;
    }

    @Override
    public String toString() {
        return "None";
    }
}
