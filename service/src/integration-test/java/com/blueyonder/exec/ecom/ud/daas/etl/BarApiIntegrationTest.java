package com.blueyonder.exec.ecom.ud.daas.etl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blueyonder.service.common.liam.testing.IntegrationTestActiveProfileResolver;
import com.blueyonder.service.common.liam.testing.WithIdentity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(resolver = IntegrationTestActiveProfileResolver.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = ExecUdDaasEtlServiceApplication.class
)
@AutoConfigureMockMvc
@WithIdentity
public class BarApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBarsProcess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/bars/process?fulfillmentCenterId=FC1"))
                .andExpect(status().isNoContent());
    }
}
