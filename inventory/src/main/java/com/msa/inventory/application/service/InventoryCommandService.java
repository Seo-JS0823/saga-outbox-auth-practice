package com.msa.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.inventory.adapter.messaging.OrderCreatedEvent;
import com.msa.inventory.domain.port.out.InventoryCommandPort;
import com.msa.inventory.domain.result.InventoryResult.ReservationResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryCommandService {

	private final InventoryCommandPort inventoryCommandPort;
	
	@Transactional
	public ReservationResult reserve(OrderCreatedEvent orderCreatedEvent) {
		return inventoryCommandPort.reserve(orderCreatedEvent);
	}
	
	
	
}
