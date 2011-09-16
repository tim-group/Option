package com.youdevise.util.option;

import com.youdevise.util.Function;

import java.util.List;

public interface OptionList<E> extends List<Option<E>> {
    <M> OptionList<M> map(Function<E, M> function);
    <M> OptionList<M> flatMap(Function<E, Option<M>> function);
    List<E> flatten();
}
