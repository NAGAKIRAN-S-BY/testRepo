package com.blueyonder.exec.ecom.ud.daas.etl.controller;

import ch.qos.logback.classic.Level;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;

import com.blueyonder.exec.ecom.boot.commons.core.logging.LogCaptureExtension;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(LogCaptureExtension.class)
class EgressControllerTests {
    private static final String EGRESS_ENTITY_TYPE = "DespatchAdvice";
    private EgressController controller = new EgressController(new ObjectMapper());

    @Test
    void receiveEgress(LogCaptureExtension.LogCaptureHelper logCaptureHelper) {
        logCaptureHelper.addLogger(Level.DEBUG, EgressController.class);

        var mockEntity = Map.of("entityId", "e27607aa-50c8-46c0-90e1-d49928d08533");
        var response = controller.receiveEgress(EGRESS_ENTITY_TYPE, mockEntity);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertEquals(1, logCaptureHelper.getLogMessages().size());
        assertEquals("Received egress for entity [DespatchAdvice] with entity id [e27607aa-50c8-46c0-90e1-d49928d08533]",
                logCaptureHelper.getLogMessages().get(0));
    }

    @Test
    void receiveEgressWithNoEntityId(LogCaptureExtension.LogCaptureHelper logCaptureHelper) {
        logCaptureHelper.addLogger(Level.DEBUG, EgressController.class);

        var mockEntity = Map.of();
        var response = controller.receiveEgress(EGRESS_ENTITY_TYPE, mockEntity);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertEquals(1, logCaptureHelper.getLogMessages().size());
        assertEquals("Received egress for entity [DespatchAdvice] with entity id [<MISSING>]",
                logCaptureHelper.getLogMessages().get(0));
    }
}
