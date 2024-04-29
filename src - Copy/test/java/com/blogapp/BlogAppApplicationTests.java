package com.blogapp;

import com.blogapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {
		System.out.println(userRepository.getClass().getName() + " " + userRepository.getClass().getPackageName());
	}

}
