package com.blueyonder.exec.ecom.ud.daas.etl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

/**
 * Foo domain model.  (SAMPLE OF DOMAIN MODEL POJO)
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Foo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    @Getter @Setter
    private String fulfillmentCenterId;

    @Column
    @Getter @Setter
    private String bar;

    /**
     * This method is here to provide some functionality for pitest
     */
    public boolean sampleBehaviorMethod() {

        return bar != null;
    }
}
