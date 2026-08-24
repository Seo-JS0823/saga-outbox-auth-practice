package com.msa.inventory.domain.model;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
	
	INVENTORY_RESERVED(InventoryStatus.RESERVED, "inventory.reserved.v1", "INVENTORY_RESERVATION"),
	
	INVENTORY_REJECTED(InventoryStatus.REJECTED, "inventory.rejected.v1", "INVENTORY_RESERVATION"),
	
	
	;
	private final InventoryStatus status;
	
	private final String topic;
	
	private final String aggregateType;
	
	static EventType matches(InventoryStatus status) {
		return Arrays.stream(values())
				.filter(eventType -> Objects.equals(eventType.status, status))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("이벤트를 발행할 수 있는 상태가 아닙니다."));
	}
	
}
