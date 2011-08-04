package com.youdevise.util.option;

import com.youdevise.util.Function;
import com.youdevise.util.Function0;
import com.youdevise.util.Predicate;
import com.youdevise.util.Unit;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.argument;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

public class Some<T> extends Option<T> {

    private final T value;

    public static <M> Option<M> Some(M value) {
        return new Some<M>(value);
    }

    private Some(T value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getOrElse(T defaultValue) {
        return get();
    }

    @Override
    public T getOrElse(Function0<T> alternative) {
        return get();
    }

    @Override
    public <M> Option<M> map(Function<T, M> function) {
        return Some(function.apply(get()));
    }

    @Override
    public <M> Option<M> map(M lambdaArgument) {
        return Some(argumentValue(lambdaArgument));
    }

    private <M> M argumentValue(M lambdaArgument) {
        return argument(lambdaArgument).evaluate(get());
    }

    private Object[] parameters(Object[] lambdaArguments) {
        return transform(asList(lambdaArguments), new Function<Object, Object>() {
            @Override public Object apply(Object from) {
                return argumentValue(from);
            }
        }).toArray(new Object[lambdaArguments.length]);
    }

    @SuppressWarnings("rawtypes")
    private Class[] parameterClasses(Object[] lambdaArguments) {
        return transform(asList(lambdaArguments), new Function<Object, Class<?>>() {
            @Override public Class<?> apply(Object from) {
                return from.getClass();
            }
        }).toArray(new Class[lambdaArguments.length]);
    }

    private static <T, M> List<M> transform(List<T> objects, Function<T, M> function) {
        List<M> result = new ArrayList<M>();
        for (T object: objects) {
            result.add(function.apply(object));
        }
        return result;
    }

    @Override
    public <M> Option<M> flatMap(Function<T, Option<M>> function) {
        return function.apply(get());
    }

    @Override
    public <M> Option<M> flatMap(Option<M> lambdaArgument) {
        return argumentValue(lambdaArgument);
    }

    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return predicate.apply(get()) ? this : None();
    }

    private Option<T> None() {
        return None.None();
    }

    @Override
    public Option<T> filter(Boolean lambdaArgument) {
        return argumentValue(lambdaArgument) ? this : None();
    }

    @Override
    public Option<T> orElse(Option<T> alternative) {
        return this;
    }

    @Override
    public Option<T> orElse(Function0<Option<T>> alternative) {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> iterator() {
        return asList(get()).iterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> toList() {
        return asList(get());
    }

    @Override
    public void execute(Unit<T> unit) {
        unit.execute(get());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Some some = (Some) o;

        if (value != null ? !value.equals(some.value) : some.value != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public Set<T> toSet() {
        return singleton(get());
    }

    @Override
    public String toString() {
        return "Some{" +
                "value=" + value +
                '}';
    }
}
