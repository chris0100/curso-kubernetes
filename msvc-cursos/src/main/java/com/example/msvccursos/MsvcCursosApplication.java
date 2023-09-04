package com.example.msvccursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients
public class MsvcCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCursosApplication.class, args);
	}

}
