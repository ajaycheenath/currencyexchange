package com.af.bitcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.af.bitcoin.entity.ExchangeRate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is an implementation of Exchange Rate Provide class to fetch current exchange rate from an external endpoint
 * In future if there is need to change the endpoint, please create a new implementation
 * @author ajay_francis
 *
 */
@Component
public class ExchangeRateProviderImpl implements ExchangeRateProvider{

	@Autowired
	private RestTemplate  restTemplate;

	private static final String CURRENT_EXCHANGE_RATE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	private Logger LOGGER = LoggerFactory.getLogger(ExchangeRateProviderImpl.class);

	
	@Override
	public ExchangeRate getCurrentRate(String currency) throws Exception{
		//TODO: add valid currency check
		ExchangeRate currencyExchangeRate = null;
		//TODO: Please note if the fetching current exchange rate code takes more time than the scheduler interval,
		//scheduled tasks may get piled up. To avoid that make sure to set HTTP timeout
		ResponseEntity<String> response
		= restTemplate.getForEntity(CURRENT_EXCHANGE_RATE_URL, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode bpiNode = root.path("bpi");
		JsonNode currencyNode = bpiNode.path(currency.trim());
		JsonNode rateNode = currencyNode.path("rate_float");
		String rateASText = rateNode.asText();
		LOGGER.debug("Current exchange rate for currency {} is {}", currency, rateASText);
		BigDecimal rate = new BigDecimal(rateASText);
		currencyExchangeRate = new ExchangeRate(currency, rate, new Date());
		return currencyExchangeRate;
	}
}
