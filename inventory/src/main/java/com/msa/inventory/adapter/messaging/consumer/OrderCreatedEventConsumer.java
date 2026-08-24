package com.msa.inventory.adapter.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.msa.inventory.adapter.messaging.EventMapper;
import com.msa.inventory.adapter.messaging.OrderCreatedEvent;
import com.msa.inventory.application.service.InventoryCommandService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventConsumer {

	private final EventMapper eventMapper;
	
	private final InventoryCommandService inventoryCommandService;
	
	
	
	@KafkaListener(
			topics = "${app.kafka.topic.order-created}",
			groupId = "${spring.kafka.consumer.group-id}"
	)
	public void consume(String payload) {
		OrderCreatedEvent event = eventMapper.fromJson(payload, OrderCreatedEvent.class);
		
		inventoryCommandService.reserve(event);
	}
	
	
	
	
}
