package com.blueyonder.exec.ecom.execud-daas-etl.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueyonder.exec.ecom.execud-daas-etl.api.EgressApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EgressController implements EgressApi {
    private ObjectMapper objectMapper;

    public EgressController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Void> receiveEgress(String entityType, Object body) {
        if (log.isDebugEnabled()) {
            var entityId = objectMapper.convertValue(body, JsonNode.class).path("entityId").asText("<MISSING>");
            log.debug("Received egress for entity [{}] with entity id [{}]", entityType, entityId);
        }
        return ResponseEntity.noContent().build();
    }
}
