package br.com.gabrielflorentino.validador_boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ValidadorBoletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidadorBoletoApplication.class, args);
	}

}
