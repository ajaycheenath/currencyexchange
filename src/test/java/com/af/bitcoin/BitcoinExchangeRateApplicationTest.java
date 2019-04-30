package com.af.bitcoin;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.af.bitcoin.entity.ExchangeRate;
import com.af.bitcoin.service.BitcoinExchangeService;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BitcoinExchangeRateApplicationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BitcoinExchangeService service; 

	/**
	 * verify whether the application context is set
	 */
	@Test
	public void verifyContextInitiated() throws Exception {
		assertNotNull(applicationContext);
	}

	@Test
	public void verifyLatestCurrencyRateGetRestEndPoint() throws Exception {
		ExchangeRate exchangeRate = new ExchangeRate("USD", BigDecimal.valueOf(1000), new Date());
		Mockito.doReturn(exchangeRate).when(service).getCurrentExchangeRate(null);
		mockMvc.perform(get("/rate/latest")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.rate").value("1000"));
	}

	@Test
	public void verifyGetExchangeRatesBetweenDatesEndPoint() throws Exception {
		List<ExchangeRate> rates = new ArrayList<ExchangeRate>();
		ExchangeRate exchangeRate = new ExchangeRate("USD", BigDecimal.valueOf(1000), new Date());
		rates.add(exchangeRate);
		Mockito.doReturn(rates).when(service).getExchangeRates(anyString(), any(Date.class), any(Date.class));
		mockMvc.perform(get("/rate/10012019/10052019/USD")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
	}


}
