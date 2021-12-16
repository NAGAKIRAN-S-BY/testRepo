package com.blueyonder.exec.ecom.ud.daas.etl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueyonder.exec.ecom.execud_daas_etl.api.BarsApi;
import com.blueyonder.exec.ecom.ud.daas.etl.rbac.LiamPermissions;

/**
 * Implements endpoints for the Bar api.
 */
@RestController
@RequestMapping("/v1")
public class BarController implements BarsApi {
    /**
     * This endpoint is responsible for processing some thing within Bars.
     * @return Empty ResponseEntity
     */
    @PreAuthorize(LiamPermissions.MANAGE)
    @Override
    public ResponseEntity<Void> process(String fulfillmentCenterId) {
        return ResponseEntity.noContent().build();
    }
}
