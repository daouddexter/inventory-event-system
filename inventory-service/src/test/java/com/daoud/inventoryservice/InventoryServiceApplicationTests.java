package com.daoud.inventoryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = "eureka.client.enabled=false")
class InventoryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
