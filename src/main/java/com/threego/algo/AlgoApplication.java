package com.threego.algo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AlgoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlgoApplication.class, args);
	}
}