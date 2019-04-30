package com.af.bitcoin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Entity POJO represents EXCHANGERATE table object 
 * @author ajay_francis
 *
 */
@Entity
@Table(name = "EXCHANGERATE")
public class ExchangeRate {

	@Id
	@GeneratedValue
	@Column(name = "Id", nullable = false)
	private Long id;

	@Column(name = "currency", length = 3, nullable = false)
	private String currency;

	@Column(name = "rate", nullable = false)
	private BigDecimal rate;

	@Column(name = "sample_date")
	private Date sampleDate;

	public ExchangeRate() {

	}

	public ExchangeRate(String currency, BigDecimal rate, Date sampleDate) {
		// TODO Auto-generated constructor stub
		this.currency = currency;
		this.rate = rate;
		this.sampleDate = sampleDate;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Date getSampleDate() {
		return sampleDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}

}
