package com.msa.inventory.domain.result;

import java.util.UUID;

import com.msa.inventory.domain.model.InventoryReservation;
import com.msa.inventory.domain.model.InventoryStatus;

public final class InventoryResult {

	public record ReservationResult(
			UUID orderId,
			UUID productId,
			Integer quantity,
			InventoryStatus status,
			String failureMessage
	) {
		
		public static ReservationResult from(InventoryReservation reservation) {
			return new ReservationResult(
					reservation.getOrderId(),
					reservation.getProductId(),
					reservation.getQuantity(),
					reservation.getStatus(),
					reservation.getFailureMessage()
			);
		}
	}
	
	
}
