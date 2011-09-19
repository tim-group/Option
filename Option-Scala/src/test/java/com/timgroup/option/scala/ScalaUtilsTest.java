package com.timgroup.option.scala;

import com.timgroup.option.Some;
import org.junit.Test;
import scala.Option;

import static com.timgroup.option.None.None;
import static com.timgroup.option.scala.ScalaUtils.toJava;
import static com.timgroup.option.scala.ScalaUtils.toScala;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScalaUtilsTest {
    @Test
    public void toScalaSome() throws Exception {
        final scala.Option<String> expected = new scala.Some<String>("expected");
        assertThat(toScala(Some.Some("expected")), is(expected));
    }

    @Test
    public void toScalaNone() throws Exception {
        final scala.Option<String> expected = (Option) scala.None$.MODULE$;
        assertThat(toScala(None(String.class)), is(expected));
    }

    @Test
    public void toJavaSome() throws Exception {
        assertThat(toJava(new scala.Some<String>("expected")), is(Some.Some("expected")));
    }

    @Test
    public void toJavaSone() throws Exception {
        final Option<String> scalaNone = (Option) scala.None$.MODULE$;
        assertThat(toJava(scalaNone), is(None(String.class)));
    }
}
