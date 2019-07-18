package com.crypto.repository;

import com.crypto.model.CryptoCurrency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrency, Long> {

}
