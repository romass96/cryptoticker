package com.crypto.repository;

import com.crypto.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TokenRepository<T extends Token> extends CrudRepository<T, Long> {
    T findByToken(String token);
}
