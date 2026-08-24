package com.msa.inventory.domain.port.out;

import com.msa.inventory.domain.model.OutboxEvent;

public interface OutboxPort {

	void save(OutboxEvent outboxEvent);
}
