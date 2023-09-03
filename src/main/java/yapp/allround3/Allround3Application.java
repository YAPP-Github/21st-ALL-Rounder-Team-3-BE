package yapp.allround3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Allround3Application {

	public static void main(String[] args) {
		SpringApplication.run(Allround3Application.class, args);
	}
}
