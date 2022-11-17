package br.com.cooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CooperativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativeApplication.class, args);
	}

}
