package com.example.crypteacher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO description
 *
 * @author joanna
 */

@Entity
@Table(name = "currency")
public class CryptoCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "price")
    private BigDecimal price;

    public CryptoCurrency(LocalDateTime datetime, String symbol, BigDecimal price) {
        this.datetime = datetime;
        this.symbol = symbol;
        this.price = price;
    }

    public CryptoCurrency() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + " " + price;
    }
}
