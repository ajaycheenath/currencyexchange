package com.af.bitcoin.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.af.bitcoin.entity.ExchangeRate;

/**
 * This service abstract the information about the external service to fetch current exchange rate
 * Which makes it easy to change the endpoint to fetch current exchange rate in future without changing / touching code consumers
 * @author ajay_francis
 *
 */
@Service
public interface ExchangeRateProvider {

	Logger logger = LoggerFactory.getLogger(ExchangeRateProvider.class);

	/**
	 * This method return current exchange rate of bitcoin to currency passed as parameter
	 * @param currency
	 * @return
	 * @throws Exception 
	 */
	ExchangeRate getCurrentRate(String currency) throws Exception;
	
	
}
