package com.af.bitcoin.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.af.bitcoin.entity.ExchangeRate;

/**
 *This class is data access object implementation of ExchangeRate table
 * @author ajay_francis
 *
 */
@Service
@Transactional
public class ExchangeRateDAOImpl implements ExchangeRateDAO{
	
	@Value("${default.exchange.currency}")
	private String DEFAULT_CURRENCY;//Configurable currency code. Default is USD

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Method to save ExchangeRate object
	 * @param exchangeRate
	 */
	@Override
	public void save(ExchangeRate exchangeRate) {
		entityManager.persist(exchangeRate);
	}

	/**
	 * Method returns Latest Exchange Rate
	 */
	@Override
	public ExchangeRate getLatestExchangeRate(String currency) {
		currency = currency == null ? DEFAULT_CURRENCY : currency;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> query = builder.createQuery(ExchangeRate.class);
		
		Root<ExchangeRate> root = query.from(ExchangeRate.class);
		query.orderBy(builder.desc(root.get("id")));
		Predicate currencyQuery = builder.equal(root.get("currency"), currency);
	    query.where(currencyQuery);

	    return entityManager.createQuery(query).setMaxResults(1).getSingleResult();
	}
	/**
	 * Method returns list of exchange rates captured between startDate and endDate
	 */
	@Override
	public List<ExchangeRate> getExchangeRates(String currency, Date startDate, Date endDate) {
		currency = currency == null ? DEFAULT_CURRENCY : currency;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> query = builder.createQuery(ExchangeRate.class);
		
		Root<ExchangeRate> root = query.from(ExchangeRate.class);
		query.orderBy(builder.asc(root.get("id")));
		Predicate currencyQuery = builder.and(builder.equal(root.get("currency"), currency), 
				builder.between(root.get("sampleDate"), startDate, endDate));
	    query.where(currencyQuery);

	    return entityManager.createQuery(query).getResultList();
	}
}
