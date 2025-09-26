package com.example.cardatabase;

import com.example.cardatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CardatabaseApplicationTests {
	@Autowired    // 이게 없으면 final 선언하고 생성자를 생성했어야 했음.
	private CarController controller;

	@Test
	@DisplayName("First Example Test Case")
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
