package com.af.bitcoin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.af.bitcoin.entity.ExchangeRate;

/**
 * This interface act as Data Access Object (ExchangeRate DB table).
 * This makes the database access / implementation details hidden / abstracted to the business logic 
 * @author ajay_francis
 *
 */
@Service
public interface ExchangeRateDAO {
	
	/**
	 * Method to save ExchangeRate object
	 * @param exchangeRate
	 */
	public void save(ExchangeRate exchangeRate);
	
	/**
	 * This method return latest currency exchange rate
	 * @return
	 */
	public ExchangeRate getLatestExchangeRate(String currency);
	
	/**
	 * This method return list of exchange rates between specified start and end dates
	 * @param currency
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ExchangeRate> getExchangeRates(String currency, Date startDate, Date endDate);
	

}
