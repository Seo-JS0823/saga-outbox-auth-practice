package com.msa.inventory.domain.request;

import java.util.UUID;

public final class InventoryCommandRequest {
	
	public record InventoryCreateRequest(UUID productId, Integer stock) {}
	
}
