package com.example.crypteacher.service;

import com.example.crypteacher.model.CryptoCurrency;

import java.time.LocalDate;
import java.util.List;

/**
 * API for the Crypteacher application.
 *
 * @author jkruczkowska
 */

public interface CryptoCurrencyService {
    List<CryptoCurrency> getAllCurrencyPrices();

    CryptoCurrency getOldestPrice(String symbol);

    CryptoCurrency getNewestPrice(String symbol);

    CryptoCurrency getMinPrice(String symbol);

    CryptoCurrency getMaxPrice(String symbol);

    void save(String filename);

    List<String> getCryptosComparedByNormalizedRange();

    String getTheHighestNormalizedRangeByDate(LocalDate date);

}
