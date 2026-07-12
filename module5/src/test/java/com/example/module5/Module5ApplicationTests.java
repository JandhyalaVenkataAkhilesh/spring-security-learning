package com.example.module5;

import com.example.module5.entities.User;
import com.example.module5.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Module5ApplicationTests {

    @Autowired
    private JWTService jwtService;

	@Test
	void contextLoads() {
        User user = new User(1L,"demo@gmail.com","1234","demo");
        String token = jwtService.generateToken(user);
        System.out.println(token);
        Long id = jwtService.getUserId(token);
        System.out.println(id);
	}

}
