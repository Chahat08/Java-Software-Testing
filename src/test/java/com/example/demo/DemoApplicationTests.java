package com.example.demo;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void shouldAddNumbers() {
		// given
		int a=1;
		int b=2;

		// when
		int result = underTest.add(a, b);

		// then
		assertEquals(3, result);

	}

	@NoArgsConstructor
	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}

}
