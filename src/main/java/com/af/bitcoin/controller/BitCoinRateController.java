package com.af.bitcoin.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.af.bitcoin.entity.ExchangeRate;
import com.af.bitcoin.service.BitcoinExchangeService;

/**
 *This controller class provide REST endpoints to GET BitCoin to other currency exchange rate
 *At present this class capture only USD exchange rate but can be extended to get other currency exchange rates as well
 * @author ajay_francis
 *
 */
@RestController
@RequestMapping(value="/rate")	
public class BitCoinRateController {

	@Autowired
	private BitcoinExchangeService bitcoinExchangeService;

	private Logger LOGGER = LoggerFactory.getLogger(BitCoinRateController.class);

	/**
	 * Rest End point /rate/latest return current exchange rate for USD
	 * @param currency 
	 * @return Latest Exchange Rate 
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value= {"/latest", "/latest/{currency}"})
	public @ResponseBody ResponseEntity<ExchangeRate> currentRate(@PathVariable(value="currency", required = false) String currency) throws Exception {
		LOGGER.debug("/GET /latest/ . Currency = {}", currency);
		ExchangeRate rate = bitcoinExchangeService.getCurrentExchangeRate(currency);
		return new ResponseEntity<ExchangeRate>(rate, HttpStatus.OK);
	}
	
	/**
	 * Rest End point /rate/latest return current exchange rate for USD
	 * @param currency , startDate and endDate (Date format DDMMYYYY)
	 * @return json - list of Exchange rates between the startDate and endDate
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value= {"/{startDate}/{endDate}", "/{startDate}/{endDate}/{currency}"})
	public @ResponseBody ResponseEntity<List<ExchangeRate>> getExchangeRatesBetweenDates(@PathVariable(value="currency", required = false) String currency, @PathVariable(value="startDate", required = true) @DateTimeFormat(pattern = "ddMMyyyy") Date startDate, @PathVariable(value="endDate", required = true) @DateTimeFormat(pattern = "ddMMyyyy") Date endDate) throws Exception {
		LOGGER.debug("/GET /startDate/endDate . Currency = {}, startDate = {} , endDate = {}", currency, startDate, endDate);
		
		List<ExchangeRate> rates = bitcoinExchangeService.getExchangeRates(currency, startDate, endDate);
		return new ResponseEntity<List<ExchangeRate>>(rates, HttpStatus.OK);
	}
	
}