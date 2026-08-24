package com.msa.inventory.adapter.messaging;

import java.util.UUID;

import com.msa.inventory.domain.model.InventoryReservation;

public final class InventoryReservationEvent {
	
	// 재고 예약 성공시 이벤트
	public record InventoryReservedEvent(
			UUID eventId,
			UUID orderId,
			UUID productId,
			Integer quantity
	) {
		public static InventoryReservedEvent from(UUID eventId, InventoryReservation reserved) {
			return new InventoryReservedEvent(
					eventId,
					reserved.getOrderId(),
					reserved.getProductId(),
					reserved.getQuantity()
			);
		}
	}
	
	// 재고 예약 실패시 이벤트
	public record InventoryRejectedEvent(
			UUID eventId,
			UUID orderId,
			UUID productId,
			Integer quantity,
			String failureMessage
	) {
		public static InventoryRejectedEvent from(UUID eventId, InventoryReservation rejected) {
			return new InventoryRejectedEvent(
					eventId,
					rejected.getOrderId(),
					rejected.getProductId(),
					rejected.getQuantity(),
					rejected.getFailureMessage()
			);
		}
	}
}
