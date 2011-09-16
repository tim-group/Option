package com.timgroup.option;

import com.timgroup.functional.Function;
import com.timgroup.functional.Function0;
import com.timgroup.functional.Unit;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents a generic wrapper of an optional value.
 * The value may be either defined (represented by Some class) or undefined (represented by None class).
 */
public abstract class Option<T> implements Iterable<T> {

    /**
     * Creates an option from nullable value.
     *
     * @return None, if the value is null, Some otherwise.
     */
    @SuppressWarnings("unchecked")
    public static <M> Option<M> fromNullable(M nullableValue) {
        return nullableValue == null ? (Option<M>) None.None() : Some.Some(nullableValue);
    }

    /**
     * Determines whether the value is defined.
     */
    public abstract boolean isEmpty();

    public boolean isDefined() {
        return !isEmpty();
    }

    /**
     * Gets the value if it is defined, throws an exception otherwise.
     */
    public abstract T get();

    /**
     * Returns the value, if it is defined, or alternative value provided otherwise.
     */
    public abstract T getOrElse(T alternative);

    /**
     * Returns the value, if it is defined, or alternative value provided otherwise.
     */
    public abstract T getOrElse(Function0<T> alternative);

    /**
     * Transforms this option to another option
     * by applying the given function to the wrapped value (if it is defined) and wrapping the result.
     */
    public abstract <M> Option<M> map(Function<T, M> function);

    /**
     * Transforms the wrapped value using the given LambdaJ argument.
     */
    public abstract <M> Option<M> map(M lambdaArgument);

    /**
     * Applies the transformation (that results in an option) to the wrapped value and returns the result.
     * Results in None if the instance on which it's called is None or the result of the transformation is None.
     */
    public abstract <M> Option<M> flatMap(Function<T, Option<M>> function);

    /**
     * Applies the transformation (defined by the LambdaJ argument) to the wrapped value and returns the result.
     * Results in None if the instance on which it's called is None or the result of the transformation is None.
     */
    public abstract <M> Option<M> flatMap(Option<M> lambdaArgument);

    /**
     * Results in Some, if the wrapped value meets the predicate, None otherwise.
     */
    public abstract Option<T> filter(Function<T, Boolean> predicate);

    /**
     * Results in Some, if the lambdaJ argument value is true, None otherwise.
     */
    public abstract Option<T> filter(Boolean lambdaArgument);

    /**
     * Returns this option if it is defined, or the given alternative otherwise.
     */
    public abstract Option<T> orElse(Option<T> alternative);

    /**
     * Returns this option if it is defined, or the result of the given closure (Function0).
     */
    public abstract Option<T> orElse(Function0<Option<T>> alternative);

    /**
     * Returns an iterator containing the wrapped value or an empty iterator, if the option is not defined.
     */
    @Override
    public abstract Iterator<T> iterator();

    /**
     * Returns a list containing the wrapped value or an empty list if it is not defined.
     */
    public abstract List<T> toList();

    /**
     * Returns a set containing the wrapped value or an empty list if it is not defined.
     */
    public abstract Set<T> toSet();

    /**
     * Executes unit of code with the wrapped value as an argument, if it is defined.
     */
    public abstract void execute(Unit<T> unit);
}

