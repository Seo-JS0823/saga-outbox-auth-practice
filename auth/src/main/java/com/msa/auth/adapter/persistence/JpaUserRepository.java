package com.msa.auth.adapter.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msa.auth.domain.model.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
	
	boolean existsByNickname(String nickname);

	Optional<User> findByEmail(String email);
}
