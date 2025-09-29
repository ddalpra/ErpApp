package it.ddalpra.acme.erpApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ErpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpAppApplication.class, args);
	}

}
