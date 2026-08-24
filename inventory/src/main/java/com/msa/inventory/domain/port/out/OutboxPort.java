package com.msa.inventory.domain.port.out;

import java.util.List;

import com.msa.inventory.domain.model.OutboxEvent;

public interface OutboxPort {

	void save(OutboxEvent outboxEvent);
	
	List<OutboxEvent> findPending();
}
