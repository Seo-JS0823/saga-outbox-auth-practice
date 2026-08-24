package com.msa.inventory.adapter.messaging;

import java.util.UUID;

public final class InventoryReservationEvent {

	public record InventoryReservedEvent(
			UUID eventId,
			UUID orderId,
			UUID productId,
			Integer quantity
	) {}
	
	public record InventoryRejectedEvent(
			UUID eventId,
			UUID orderId,
			UUID productId,
			Integer quantity,
			String failureMessage
	) {}
}
