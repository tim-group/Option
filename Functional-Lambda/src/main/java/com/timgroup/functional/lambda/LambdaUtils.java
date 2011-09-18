package com.timgroup.functional.lambda;

import com.timgroup.functional.Function;

import static ch.lambdaj.Lambda.argument;

public class LambdaUtils {
    /**
     * Converts a lambda argument to Function that performs the recorded method call(s).
     */
    public static <F, T> Function<F, T> lambda(final T lambdaArgument) {
        return new Function<F, T>() {
            @Override public T apply(F from) {
                return argument(lambdaArgument).evaluate(from);
            }
        };
    }
}
