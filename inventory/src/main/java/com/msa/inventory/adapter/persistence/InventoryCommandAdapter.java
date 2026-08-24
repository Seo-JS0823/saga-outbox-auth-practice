package com.msa.inventory.adapter.persistence;

import org.springframework.stereotype.Component;

import com.msa.inventory.domain.port.out.InventoryCommandPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryCommandAdapter implements InventoryCommandPort {

	private final JpaInventoryRepository inventoryRepo;
	
	private final JpaInventoryReservationRepository reservationRepo;
	
	
}
