package com.daoud.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = "eureka.client.enabled=false")
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
