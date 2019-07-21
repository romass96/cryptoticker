package com.crypto.repository;

import com.crypto.model.CryptoCurrency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrency, Long> {
    CryptoCurrency findByShortName(String shortName);
    CryptoCurrency findByCoinMarketCapId(Long id);
}
