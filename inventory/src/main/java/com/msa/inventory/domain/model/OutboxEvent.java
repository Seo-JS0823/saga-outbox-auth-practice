package com.msa.inventory.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;

@Entity
@Table(name = "outbox_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEvent {

	@Id
	private UUID id;
	
	@Column(name = "aggregate_type", nullable = false, length = 100)
	private String aggregateType;
	
	@Column(name = "aggregate_id", nullable = false)
	private UUID aggregateId;
	
	@Column(name = "event_type", nullable = false, length = 100)
	private String eventType;
	
	@Column(name = "topic", nullable = false, length = 100)
	private String topic;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "payload", nullable = false, columnDefinition = "jsonb")
	private JsonNode payload;
	
	@Column(name = "status", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private OutboxStatus status;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@Column(name = "published_at")
	private Instant publishedAt;
	
	public static OutboxEvent pending(
			UUID eventId,
			String aggregateType,
			UUID aggregateId,
			String eventType,
			String topic,
			JsonNode payload
	) {
		OutboxEvent outbox = new OutboxEvent();
		
		outbox.id = eventId;
		outbox.aggregateType = aggregateType;
		outbox.aggregateId = aggregateId;
		outbox.eventType = eventType;
		outbox.topic = topic;
		outbox.payload = payload;
		outbox.status = OutboxStatus.PENDING;
		outbox.createdAt = Instant.now();
		outbox.publishedAt = null;
		
		return outbox;
	}
	
	public void published() {
		if(Objects.equals(this.status, OutboxStatus.PUBLISHED)) {
			return;
		}
		
		this.status = OutboxStatus.PUBLISHED;
		this.publishedAt = Instant.now();
	}
	
	
}
