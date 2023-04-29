package com.example.crypteacher.service;

import com.example.crypteacher.model.CryptoCurrency;
import com.example.crypteacher.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CryptoCurrencyServiceImplTest {

    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @InjectMocks
    private CryptoCurrencyServiceImpl cryptoCurrencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCurrencyPrices() {
        List<CryptoCurrency> expected = new ArrayList<>();
        expected.add(new CryptoCurrency(LocalDateTime.now(), "XRP", new BigDecimal("0.1")));
        when(cryptoCurrencyRepository.findAll()).thenReturn(expected);

        List<CryptoCurrency> actual = cryptoCurrencyService.getAllCurrencyPrices();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetOldestPrice() {
        CryptoCurrency expected = new CryptoCurrency(LocalDateTime.now().minusDays(1), "BTC", new BigDecimal("10000"));
        when(cryptoCurrencyRepository.findTopBySymbolOrderByDatetimeAsc("BTC")).thenReturn(expected);

        CryptoCurrency actual = cryptoCurrencyService.getOldestPrice("BTC");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNewestPrice() {
        CryptoCurrency expected = new CryptoCurrency(LocalDateTime.now(), "XRP", new BigDecimal("10000"));
        when(cryptoCurrencyRepository.findTopBySymbolOrderByDatetimeDesc("XRP")).thenReturn(expected);

        CryptoCurrency actual = cryptoCurrencyService.getNewestPrice("XRP");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMinPrice() {
        CryptoCurrency expected = new CryptoCurrency(LocalDateTime.now(), "BTC", new BigDecimal("500"));
        when(cryptoCurrencyRepository.findTopBySymbolOrderByPriceAsc("BTC")).thenReturn(expected);

        CryptoCurrency actual = cryptoCurrencyService.getMinPrice("BTC");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMaxPrice() {
        CryptoCurrency expected = new CryptoCurrency(LocalDateTime.now(), "DOGE", new BigDecimal("3000"));
        when(cryptoCurrencyRepository.findTopBySymbolOrderByPriceDesc("DOGE")).thenReturn(expected);

        CryptoCurrency actual = cryptoCurrencyService.getMaxPrice("DOGE");
    }
}