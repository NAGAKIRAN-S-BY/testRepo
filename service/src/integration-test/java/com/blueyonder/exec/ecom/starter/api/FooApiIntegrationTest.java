package com.blueyonder.exec.ecom.execud-daas-etl.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blueyonder.exec.ecom.execud-daas-etl.ExecUdDaasEtlServiceApplication;
import com.blueyonder.exec.ecom.execud-daas-etl.model.Foo;
import com.blueyonder.exec.ecom.execud-daas-etl.repository.FooRepository;
import com.blueyonder.service.common.liam.testing.IntegrationTestActiveProfileResolver;
import com.blueyonder.service.common.liam.testing.WithIdentity;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(resolver = IntegrationTestActiveProfileResolver.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = ExecUdDaasEtlServiceApplication.class
)
@AutoConfigureMockMvc
@WithIdentity
public class FooApiIntegrationTest {

    @Autowired
    private FooRepository fooRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String BAR = "bar";

    @BeforeEach
    public void beforeEach() {
        fooRepository.deleteAll();
    }

    @Test
    public void testFoosList() throws Exception {

        Foo foo = new Foo();
        foo.setBar(BAR);
        fooRepository.save(foo);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/foos")
                .queryParam("fulfillmentCenterId", "FC1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entities.length()", is(1)))
                .andExpect(jsonPath("$.entities.[0].bar", is(BAR)));

    }

    @Test
    public void testShipmentCreate() throws Exception {
        Foo foo = new Foo();
        foo.setFulfillmentCenterId("FC1");
        foo.setBar(BAR);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/foos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(foo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bar", is(BAR)));
    }
}
