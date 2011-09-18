package com.timgroup.functional.lambda;

import com.timgroup.functional.Function;
import org.junit.Test;

import static ch.lambdaj.Lambda.on;
import static com.timgroup.functional.lambda.LambdaUtils.lambda;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LambdaUtilsTest {

    @Test
    public void functionReturnedByLambdaCallsTheRecordedMethod() throws Exception {
        MyInterface myInterface = mock(MyInterface.class);

        given(myInterface.aMethod()).willReturn("expected");

        final Function<MyInterface, String> function = lambda(on(MyInterface.class).aMethod());
        assertThat(function.apply(myInterface), is("expected"));
    }

    private interface MyInterface {
        public String aMethod();
    }
}
