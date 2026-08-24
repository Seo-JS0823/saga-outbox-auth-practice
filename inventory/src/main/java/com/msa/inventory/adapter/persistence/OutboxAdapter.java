package com.msa.inventory.adapter.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.msa.inventory.domain.model.OutboxEvent;
import com.msa.inventory.domain.model.OutboxStatus;
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

	@Override
	public List<OutboxEvent> findPending() {
		return outboxRepo.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
	}
}
