package com.example.crypteacher.controller;

import com.example.crypteacher.model.CryptoCurrency;
import com.example.crypteacher.repository.CryptoCurrencyRepository;
import com.example.crypteacher.service.CryptoCurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO description
 *
 * @author joanna
 */
@Api(value = "Cryptocurrency Rest Controller")
@RestController
@RequestMapping("/currency")
public class CryptoCurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CryptoCurrencyController.class);
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    public CryptoCurrencyController(CryptoCurrencyService cryptoCurrencyService, CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    @ApiOperation(value = "Get all crypto prices ", response = CryptoCurrency.class, tags = "getAllPrices")
    @GetMapping("/price/getAll")
    public ResponseEntity<List<CryptoCurrency>> getAllPrices() {
        try {
            List<CryptoCurrency> cryptoCurrencies = new ArrayList<>(cryptoCurrencyRepository.findAll());

            return new ResponseEntity<>(cryptoCurrencies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get the oldest price for the given crypto ", response = CryptoCurrency.class, tags = "getTheOldestPrice")
    @GetMapping("/price/oldest/{symbol}")
    public CryptoCurrency getOldestPrice(@PathVariable String symbol) {
        return cryptoCurrencyService.getOldestPrice(symbol.toUpperCase());
    }

    @ApiOperation(value = "Get the newest price for the given crypto ", response = CryptoCurrency.class, tags = "getTheNewestPrice")
    @GetMapping("/price/newest/{symbol}")
    public CryptoCurrency getNewestPrice(@PathVariable String symbol) {
        return cryptoCurrencyService.getNewestPrice(symbol.toUpperCase());
    }

    @ApiOperation(value = "Get min price for the given crypto ", response = CryptoCurrency.class, tags = "getMinPrice")
    @GetMapping("/price/min/{symbol}")
    public CryptoCurrency getMinPrice(@PathVariable String symbol) {
        return cryptoCurrencyService.getMinPrice(symbol.toUpperCase());
    }

    @ApiOperation(value = "Get max price for the given crypto ", response = CryptoCurrency.class, tags = "getMaxPrice")
    @GetMapping("/price/max/{symbol}")
    public CryptoCurrency getMaxPrice(@PathVariable String symbol) {
        return cryptoCurrencyService.getMaxPrice(symbol.toUpperCase());
    }

    @ApiOperation(value = "Get normalized range of a one crypto by date ", response = CryptoCurrency.class, tags = "getTheHighestNormalizedRangeByDate")
    @GetMapping("/price/normalized/{date}")
    public ResponseEntity<String> getTheHighestNormalizedRangeByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            String crypto = cryptoCurrencyService.getTheHighestNormalizedRangeByDate(localDate);
            return new ResponseEntity<>(crypto, HttpStatus.OK);
        } catch(DateTimeParseException dte) {
            return new ResponseEntity<>("Invalid date, expecting YYYY-MM-DD",null, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get descending sorted list of all the cryptos, comparing the normalized range ", response = CryptoCurrency.class, tags = "getNormalizedRangeDesc")
    @GetMapping("/price/normalized")
    public List<String> getNormalizedRangeDesc() {
        return cryptoCurrencyService.getCryptosComparedByNormalizedRange();
    }

    @PostConstruct
    public void saveToDb() {
        String pathToFolder = "src/main/resources/data";
        for (File file : getCsvFilesFromFolder(pathToFolder)) {
            logger.info("Loading a " + file.getName() + " into the cryptodb");
            cryptoCurrencyService.save("data/" + file.getName());
        }

    }

    private File[] getCsvFilesFromFolder(String pathToFolder) {
        File dataResourceFolder = new File(pathToFolder);
        return dataResourceFolder.listFiles((data, name) -> name.toLowerCase().endsWith(".csv"));
    }
}
