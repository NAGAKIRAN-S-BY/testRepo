package com.blueyonder.exec.ecom.ud.daas.etl.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BarControllerTests {
    private static final String FC = "FULFILLMENT_CENTER";

    private BarController controller;

    @BeforeEach
    void init() {
        controller = new BarController();
    }

    @Test
    void processBars() {
        assertEquals(HttpStatus.NO_CONTENT, controller.process(FC).getStatusCode());
    }
}
