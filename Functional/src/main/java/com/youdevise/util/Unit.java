package com.youdevise.util;

/**
 * Represents a unit of code with a single parameter.
 */
public interface Unit<T> {
    void execute(T argument);
}
