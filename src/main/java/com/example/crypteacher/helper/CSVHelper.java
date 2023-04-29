package com.example.crypteacher.helper;

import com.example.crypteacher.model.CryptoCurrency;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper service parsing data given in files in the .csv format
 *
 * @author jkruczkowska
 */
@Service
public class CSVHelper {

    public static String TYPE = "text/csv";

    public static List<CryptoCurrency> saveDataFromCsv(@NonNull InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CryptoCurrency cryptoCurrency = new CryptoCurrency(
                        convertTimestampToLocalDateTime(csvRecord.get("timestamp")),
                        csvRecord.get("symbol"),
                        BigDecimal.valueOf(Double.parseDouble(csvRecord.get("price")))
                );
                cryptoCurrencies.add(cryptoCurrency);
            }

            return cryptoCurrencies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    private static LocalDateTime convertTimestampToLocalDateTime(String timestamp) {
        long timestampFileInput = Long.parseLong(timestamp);
        Timestamp parsedToTimestamp = new Timestamp(timestampFileInput);
        return parsedToTimestamp.toLocalDateTime();
    }
}
