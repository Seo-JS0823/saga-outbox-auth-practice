package com.msa.inventory.adapter.persistence;

import org.springframework.stereotype.Component;

import com.msa.inventory.domain.model.OutboxEvent;
import com.msa.inventory.domain.port.out.OutboxPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxAdapter implements OutboxPort {
	
	private final JpaOutboxRepository outboxRepo;
	
	@Override
	public void save(OutboxEvent outboxEvent) {
		outboxRepo.save(outboxEvent);
	}
}
