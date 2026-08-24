package com.msa.auth.adapter.persistence;

import org.springframework.data.repository.CrudRepository;

import com.msa.auth.domain.model.PhantomToken;

public interface RedisTokenRepository extends CrudRepository<PhantomToken, String> {

}
