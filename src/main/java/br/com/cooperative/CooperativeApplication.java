package br.com.cooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
public class CooperativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativeApplication.class, args);
	}

}
