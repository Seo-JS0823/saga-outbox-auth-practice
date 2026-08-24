package com.msa.inventory.adapter.persistence;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.inventory.adapter.messaging.EventMapper;
import com.msa.inventory.adapter.messaging.InventoryReservationEvent.InventoryRejectedEvent;
import com.msa.inventory.adapter.messaging.InventoryReservationEvent.InventoryReservedEvent;
import com.msa.inventory.adapter.messaging.OrderCreatedEvent;
import com.msa.inventory.domain.model.EventType;
import com.msa.inventory.domain.model.InventoryReservation;
import com.msa.inventory.domain.model.OutboxEvent;
import com.msa.inventory.domain.port.out.InventoryCommandPort;
import com.msa.inventory.domain.port.out.OutboxPort;
import com.msa.inventory.domain.result.InventoryResult.ReservationResult;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class InventoryCommandAdapter implements InventoryCommandPort {

	private final JpaInventoryRepository inventoryRepo;
	
	private final JpaInventoryReservationRepository reservationRepo;
	
	private final OutboxPort outboxPort;
	
	private final EventMapper eventMapper;

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
				InventoryReservation reserved = InventoryReservation.reserved(
						orderCreatedEvent.orderId(),
						orderCreatedEvent.productId(),
						orderCreatedEvent.quantity()
				);
				
				reservationRepo.save(reserved);
				
				saveOutbox(reserved);
				
				return ReservationResult.from(reserved);
			}
			
			// 재고 예약 실패 - 재고 부족
			case 0 -> {
				InventoryReservation rejected = InventoryReservation.rejected(
						orderCreatedEvent.orderId(),
						orderCreatedEvent.productId(),
						orderCreatedEvent.quantity(),
						"재고 부족"
				);
				
				reservationRepo.save(rejected);
				
				saveOutbox(rejected);
				
				return ReservationResult.from(rejected);
			}
			
			// 서버 에러
			default -> {
				throw new RuntimeException("Server Error");
			}
		}
	}
	
	private void saveOutbox(InventoryReservation reservation) {
		UUID eventId = UUID.randomUUID();
		
		EventType eventType = reservation.getEventType();
		
		switch (eventType) {
			case INVENTORY_RESERVED -> {
				InventoryReservedEvent event = InventoryReservedEvent.from(eventId, reservation);
				
				JsonNode payload = eventMapper.toJsonNode(event);
				
				OutboxEvent outboxEvent = OutboxEvent.pending(
						eventId,
						eventType.getAggregateType(),
						reservation.getOrderId(),
						eventType.name(),
						eventType.getTopic(),
						payload
				);
				
				outboxPort.save(outboxEvent);
			}
			
			case INVENTORY_REJECTED -> {
				InventoryRejectedEvent event = InventoryRejectedEvent.from(eventId, reservation);
				
				JsonNode payload = eventMapper.toJsonNode(event);
				
				OutboxEvent outboxEvent = OutboxEvent.pending(
						eventId,
						eventType.getAggregateType(),
						reservation.getOrderId(),
						eventType.name(),
						eventType.getTopic(),
						payload
				);
				
				outboxPort.save(outboxEvent);
			}
			
			default -> {
				throw new IllegalArgumentException("이벤트를 발행할 수 있는 상태가 아닙니다.");
			}
		}
	}
	
}
