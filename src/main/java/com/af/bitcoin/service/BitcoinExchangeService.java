package com.af.bitcoin.service;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.af.bitcoin.dao.ExchangeRateDAO;
import com.af.bitcoin.entity.ExchangeRate;

/**
 This service class provides latest currency exchange rate and exchange rates between two date ranges
 * @author ajay_francis
 *
 */
@Service
public class BitcoinExchangeService {
	
	
	@Autowired
	private ExchangeRateProvider exchangeRateProvider;
	
	@Autowired
	private ExchangeRateDAO exchangeRateDAO;
	
	@Value("${default.exchange.currency}")
	private String DEFAULT_CURRENCY;//Provide an option to extend the code

	private ExchangeRate currentExchangeRate = null;
	private Logger LOGGER = LoggerFactory.getLogger(BitcoinExchangeService.class);

	/**
	 * This method return current exchange rate for bitcoin to USD.
	 * TODO: In order to avoid round trips to DB, use a Map to hold the latest currency exchange value. Example USD --> $5000.890
	 * @param currency, if null then default to USD
	 * @return
	 */
	public ExchangeRate getCurrentExchangeRate(String currency) {
		//return this.currentExchangeRate;
		return exchangeRateDAO.getLatestExchangeRate(currency);//TODO: when other currency exchange rates are supported get the current data from DB or keep a Map
	}
	
	/**
	 * This method return list of exchange rate for a specific currency between given two date ranges
	 * If the currency passed is null then will be defaulted to USD
	 * @param currency
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ExchangeRate> getExchangeRates(String currency, Date startDate, Date endDate) {
		return exchangeRateDAO.getExchangeRates(currency, startDate, endDate);
	}
	
	/**
	 * This scheduled function gets executed in fixed interval
	 * Interval of execution is configured as key exchange.rate.capture.interval in property file.
	 * When the scheduler kicks in, it fetches current currency exchange rate from an external service
	 */
	@Scheduled(fixedRateString = "${exchange.rate.capture.interval}")
    public void captureCurrentExchangeRate() {
		LOGGER.debug("About to capture current exchange rate ");
		try {
			//TODO: Please note if the fetching current exchange rate code takes more time than the scheduler interval,
			//scheduled tasks may get piled up. To avoid that make sure to set HTTP timeout
			this.currentExchangeRate = exchangeRateProvider.getCurrentRate(DEFAULT_CURRENCY);
			exchangeRateDAO.save(currentExchangeRate);
		} catch (Exception e) {
			LOGGER.error("Error while getting current exchange rate ", e);
		}
    }
}