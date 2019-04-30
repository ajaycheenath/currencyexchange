package com.af.bitcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class bootstraps Bitcoin to other currency exchange rate application
 * @author ajay_francis
 *
 */
@SpringBootApplication
@EnableScheduling
public class BitcoinExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinExchangeRateApplication.class, args);
	}
}