package com.msa.inventory.adapter.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msa.inventory.domain.model.OutboxEvent;
import com.msa.inventory.domain.model.OutboxStatus;

public interface JpaOutboxRepository extends JpaRepository<OutboxEvent, UUID> {

	List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
