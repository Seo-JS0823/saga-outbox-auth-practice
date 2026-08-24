package com.msa.inventory.adapter.messaging;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class EventMapper {

	private final JsonMapper jsonMapper;
	
	public JsonNode toJsonNode(Object event) {
		return jsonMapper.valueToTree(event);
	}
	
	public <T> T fromJson(String payload, Class<T> eventType) {
		return jsonMapper.readValue(payload, eventType);
	}
}
