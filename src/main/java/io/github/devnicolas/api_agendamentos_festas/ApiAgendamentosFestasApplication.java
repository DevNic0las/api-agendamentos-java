package io.github.devnicolas.api_agendamentos_festas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class ApiAgendamentosFestasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAgendamentosFestasApplication.class, args);
	}


	@GetMapping("/")
	public ResponseEntity<?> teste(){
		return ResponseEntity.ok().body("ok");
	}

}
