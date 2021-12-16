package com.blueyonder.exec.ecom.starter.api;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blueyonder.bydm.DespatchAdviceType;
import com.blueyonder.bydm.ReceivingAdviceType;
import com.blueyonder.exec.ecom.ud.daas.etl.ExecUdDaasEtlServiceApplication;
import com.blueyonder.service.common.liam.testing.IntegrationTestActiveProfileResolver;
import com.blueyonder.service.common.liam.testing.WithIdentity;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(resolver = IntegrationTestActiveProfileResolver.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = ExecUdDaasEtlServiceApplication.class
)
@AutoConfigureMockMvc
@WithIdentity
public class EgressApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDespatchAdvice() throws Exception {
        DespatchAdviceType type = new DespatchAdviceType().withEntityId(UUID.randomUUID().toString());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/egress/despatchAdvice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testReceivingAdvice() throws Exception {
        ReceivingAdviceType type = new ReceivingAdviceType().withEntityId(UUID.randomUUID().toString());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/egress/receivingAdvice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(type)))
                .andExpect(status().isNoContent());
    }
}
