package com.msa.inventory.adapter.messaging;

import java.util.UUID;

public record OrderCreatedEvent(
		UUID orderId,
		UUID productId,
		Integer quantity
) {

}
