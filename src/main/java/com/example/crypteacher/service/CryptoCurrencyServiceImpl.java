package com.example.crypteacher.service;

import com.example.crypteacher.helper.CSVHelper;
import com.example.crypteacher.model.CryptoCurrency;
import com.example.crypteacher.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation provided to return data
 * depending on user params input.
 *
 * @author jkruczkowska
 */
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    public CryptoCurrencyServiceImpl(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    public void save(String file) {
        List<CryptoCurrency> cryptoCurrencies = CSVHelper.saveDataFromCsv(Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(file)));
        cryptoCurrencyRepository.saveAll(cryptoCurrencies);
    }


    public List<CryptoCurrency> getAllCurrencyPrices() {
        return cryptoCurrencyRepository.findAll();
    }

    @Override
    public CryptoCurrency getOldestPrice(String symbol) {
        return cryptoCurrencyRepository.findTopBySymbolOrderByDatetimeAsc(symbol);
    }

    @Override
    public CryptoCurrency getNewestPrice(String symbol) {
        return cryptoCurrencyRepository.findTopBySymbolOrderByDatetimeDesc(symbol);
    }

    @Override
    public CryptoCurrency getMinPrice(String symbol) {
        return cryptoCurrencyRepository.findTopBySymbolOrderByPriceAsc(symbol);
    }

    @Override
    public CryptoCurrency getMaxPrice(String symbol) {
        return cryptoCurrencyRepository.findTopBySymbolOrderByPriceDesc(symbol);
    }

    @Override
    public List<String> getCryptosComparedByNormalizedRange() {
        Map<String, BigDecimal> normalizedRangeCrypto = getNormalizedRangeValues();
        List<String> sortedCrypto = normalizedRangeCrypto
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.toList());
        return sortedCrypto;
    }

    private Map<String, BigDecimal> getNormalizedRangeValues() {
        List<String> cryptos = cryptoCurrencyRepository.findCryptoCurrenciesBySymbol();
        BigDecimal normalizedRange;
        Map<String, BigDecimal> normalizedRangeCrypto = new HashMap<>();
        for (String cryptoName : cryptos) {
            normalizedRange = getNormalizedRange(cryptoName);
            normalizedRangeCrypto.put(cryptoName, normalizedRange);
        }
        return normalizedRangeCrypto;
    }

    private BigDecimal getNormalizedRange(String cryptoName) {
        BigDecimal minPrice;
        BigDecimal maxPrice;
        BigDecimal normalizedRange;
        maxPrice = getMaxPrice(cryptoName).getPrice();
        minPrice = getMinPrice(cryptoName).getPrice();
        normalizedRange = maxPrice.subtract(minPrice).divide(minPrice, RoundingMode.HALF_EVEN);
        return normalizedRange;
    }

    @Override
    public String getTheHighestNormalizedRangeByDate(LocalDate date) {
        Map<String, BigDecimal> normalizedRangeCrypto = getNormalizedRangeValues();
        String highestNormalizedRange = normalizedRangeCrypto
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> e.getKey() + ": " + e.getValue())
                .findFirst()
                .orElse("not found");
        return highestNormalizedRange;
    }
}
