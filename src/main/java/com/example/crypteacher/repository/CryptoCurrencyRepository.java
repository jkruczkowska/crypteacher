package com.example.crypteacher.repository;

import com.example.crypteacher.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    List<CryptoCurrency> findBySymbol(String symbol);
    CryptoCurrency findTopBySymbolOrderByPriceDesc(String symbol);
    CryptoCurrency findTopBySymbolOrderByPriceAsc(String symbol);
    CryptoCurrency findTopBySymbolOrderByDatetimeDesc(String symbol);
    CryptoCurrency findTopBySymbolOrderByDatetimeAsc(String symbol);
    @Query("SELECT DISTINCT symbol FROM CryptoCurrency ")
    List<String> findCryptoCurrenciesBySymbol();
}
