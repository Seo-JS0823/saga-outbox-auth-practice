package com.msa.inventory.adapter.messaging.producer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.msa.inventory.domain.model.OutboxEvent;
import com.msa.inventory.domain.port.out.OutboxPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

	private final OutboxPort outboxPort;
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	@Scheduled(fixedDelayString = "${app.outbox.publish-delay}")
	@Transactional
	public void publishPendingEvents() {
		List<OutboxEvent> pendingEvents = outboxPort.findPending();
		
		for(OutboxEvent outboxEvent : pendingEvents) {
			publish(outboxEvent);
		}
	}

	private void publish(OutboxEvent outboxEvent) {
		try {
			kafkaTemplate.send(
					outboxEvent.getTopic(),
					outboxEvent.getAggregateId().toString(),
					outboxEvent.getPayload().toString()
			).get(5, TimeUnit.SECONDS);
			
			outboxEvent.published();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			
			log.error("Outbox 이벤트 발행 중 스레드 중단. eventId = {}",
					outboxEvent.getId(),
					e
			);
		} catch (Exception e) {
			log.error("Outbox 이벤트 발행 실패. eventId = {}",
					outboxEvent.getId(),
					e
			);
		}
	}
}
