# Crypto Recommendation Service - Crypteacher

##
`If you're new to cryptocurrencies - don't worry! We are here to help you choose the best cryptocurrency to invest in.

This service provides cryptocurrency price statistics so, you can see what can make you rich and happy!

Just get started, invest and count your income!
TO THE MOON!!! ` :rocket:

## Running directly

```sh
./mvnw spring-boot:run
```

## Creating Docker Image

```sh
docker build -t xm/crypteacher .
```

## Running Docker Container

```sh
docker run -p 8080:8080 xm/crypteacher
```

## Swagger API spec

[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

## Example Endpoints

- Enpoints returning the oldest/newest/min/max values for a requested crypto - examples:
    - > Minimum for Bitcoin: http://localhost:8080/currency/price/min/BTC
    - > Maximum for Litecoin: http://localhost:8080/currency/price/max/LTC
    - > Newest for Ripple: http://localhost:8080/currency/price/newest/XRP
    - > Oldest for DOGE: http://localhost:8080/currency/price/oldest/DOGE

- Descending sorted list of all the cryptos, comparing the normalized range, i.e. `(max-min)/min`
  > http://localhost:8080/currency/price/normalized

- Crypto with the highest normalized range for a specific day:
  > http://localhost:8080/currency/price/normalized/2022-01-15