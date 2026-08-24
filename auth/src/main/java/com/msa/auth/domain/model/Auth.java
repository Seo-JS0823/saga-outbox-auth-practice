package com.msa.auth.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authId;
	
	
}
