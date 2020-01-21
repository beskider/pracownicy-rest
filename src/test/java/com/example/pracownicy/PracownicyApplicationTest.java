package com.example.pracownicy;

import com.example.pracownicy.controller.PracownicyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class PracownicyApplicationTest {

	@Autowired
	private PracownicyController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
