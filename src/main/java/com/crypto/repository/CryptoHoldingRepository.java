package com.crypto.repository;

import com.crypto.model.CryptoHolding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoHoldingRepository extends CrudRepository<CryptoHolding, Long> {
}
