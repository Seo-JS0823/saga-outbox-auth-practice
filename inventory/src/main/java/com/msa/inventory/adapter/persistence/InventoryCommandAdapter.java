package com.msa.inventory.adapter.persistence;

import org.springframework.stereotype.Component;

import com.msa.inventory.adapter.messaging.OrderCreatedEvent;
import com.msa.inventory.domain.model.InventoryReservation;
import com.msa.inventory.domain.port.out.InventoryCommandPort;
import com.msa.inventory.domain.result.InventoryResult.ReservationResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryCommandAdapter implements InventoryCommandPort {

	private final JpaInventoryRepository inventoryRepo;
	
	private final JpaInventoryReservationRepository reservationRepo;

	@Override
	public ReservationResult reserve(OrderCreatedEvent orderCreatedEvent) {
		InventoryReservation reservation = reservationRepo.findById(orderCreatedEvent.orderId())
				.orElse(null);
		
		if(reservation != null) {
			return ReservationResult.from(reservation);
		}
		
		int updatedCount = inventoryRepo.decreaseIfEnough(
				orderCreatedEvent.productId(),
				orderCreatedEvent.quantity()
		);
		
		switch (updatedCount) {
			// 재고 예약 성공
			case 1 -> {
				InventoryReservation reserved = InventoryReservation.reserved(orderCreatedEvent);
				
				reservationRepo.save(reserved);
				
				// TODO: Outbox 위치할 곳
				
				return ReservationResult.from(reserved);
			}
			
			// 재고 예약 실패 - 재고 부족
			case 0 -> {
				InventoryReservation rejected = InventoryReservation.rejected(orderCreatedEvent, "재고 부족");
				
				reservationRepo.save(rejected);
				
				// TODO: Outbox 위치할 곳
				
				return ReservationResult.from(rejected);
			}
			
			// 서버 에러
			default -> {
				throw new RuntimeException("Server Error");
			}
		}
	}
	
	
}
