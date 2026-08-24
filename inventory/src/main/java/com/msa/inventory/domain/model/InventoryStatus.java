package com.msa.inventory.domain.model;

/*
 * 상태 전이 표
 * 
 * 정상 프로세스
 * 요청 -> RESERVED -> CONFIRMED
 * 
 * 보상 트랜잭션 실행으로 가용 재고 반환
 * 요청 -> RESERVED -> RELEASED
 * 
 * 재고 예약을 하지 못하거나 존재하지 않는 등
 * 요청 -> REJECTED
 */
public enum InventoryStatus {
	// 가용 재고에서 차감하여 보유 중
	RESERVED,
	
	// 판매 재고로 확정
	CONFIRMED,
	
	// 보상으로 가용 재고에 반환
	RELEASED,
	
	// 재고 부족 등으로 예약하지 못함
	REJECTED,
	
	;
	
	public EventType getEventType() {
		return EventType.matches(this);
	}
	
}
