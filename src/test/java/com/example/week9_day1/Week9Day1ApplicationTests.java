package com.example.week9_day1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Week9Day1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
