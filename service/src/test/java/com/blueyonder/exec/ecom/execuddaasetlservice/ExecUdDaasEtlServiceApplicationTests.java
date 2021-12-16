package com.blueyonder.exec.ecom.execud-daas-etl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ExecUdDaasEtlServiceApplicationTests {

    @Test
    void applicationLoads() {
        assertDoesNotThrow(() -> ExecUdDaasEtlServiceApplication.main(new String[]{"--server.port=0"}));
    }

}
