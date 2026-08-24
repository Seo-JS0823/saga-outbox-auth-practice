package com.msa.inventory.application.service;

import org.springframework.stereotype.Service;

import com.msa.inventory.domain.port.out.InventoryCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryCommandService {

	private final InventoryCommandPort inventoryCommandPort;
	
	
}
