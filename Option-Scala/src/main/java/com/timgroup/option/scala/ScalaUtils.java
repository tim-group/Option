package com.timgroup.option.scala;

import com.timgroup.functional.Function;
import com.timgroup.option.None;
import scala.Function1;
import scala.Option;
import scala.Some;

public class ScalaUtils {
    public static <T> Option<T> toScala(com.timgroup.option.Option<T> javaOption) {
        if (javaOption.isDefined()) {
            return new Some<T>(javaOption.get());
        }
        return (Option<T>)scala.None$.MODULE$;
    }

    public static <T> com.timgroup.option.Option<T> toJava(Option<T> scalaOption) {
        if (scalaOption.isDefined()) {
            return com.timgroup.option.Some.Some(scalaOption.get());
        }
        return None.<T>None();
    }
}
