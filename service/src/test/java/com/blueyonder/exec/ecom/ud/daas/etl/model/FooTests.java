package com.blueyonder.exec.ecom.ud.daas.etl.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooTests {

    @Test
    public void sampleTestForPiTest() {
        Foo foo = Foo.builder().bar("bar").build();
        assertTrue(foo.sampleBehaviorMethod());

        foo.setBar(null);
        assertFalse(foo.sampleBehaviorMethod());
    }
}
