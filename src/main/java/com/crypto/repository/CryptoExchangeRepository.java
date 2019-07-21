package com.crypto.repository;

import com.crypto.model.CryptoExchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CryptoExchangeRepository extends CrudRepository<CryptoExchange, Long> {
    CryptoExchange findByName(String name);

}
