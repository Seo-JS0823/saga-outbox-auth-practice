package com.msa.inventory.domain.port.out;

import com.msa.inventory.adapter.messaging.OrderCreatedEvent;
import com.msa.inventory.domain.result.InventoryResult.ReservationResult;

public interface InventoryCommandPort {

	ReservationResult reserve(OrderCreatedEvent orderCreatedEvent);
}
