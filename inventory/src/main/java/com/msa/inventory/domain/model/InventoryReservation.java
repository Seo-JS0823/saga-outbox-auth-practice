package com.msa.inventory.domain.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_inventory_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryReservation {

	@Id
	@Column(name = "order_id")
	private UUID orderId;
	
	@Column(name = "product_id", nullable = false)
	private UUID productId;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private InventoryStatus status;
	
	@Column(name = "failure_message")
	private String failureMessage;
	
	public static InventoryReservation reserved(
			UUID orderId,
			UUID productId,
			Integer quantity
	) {
		InventoryReservation inventoryReservation = new InventoryReservation();
		
		inventoryReservation.status = InventoryStatus.RESERVED;
		
		inventoryReservation.orderId = orderId;
		inventoryReservation.productId = productId;
		inventoryReservation.quantity = quantity;
		
		inventoryReservation.failureMessage = null;
		
		return inventoryReservation;
	}
	
	public static InventoryReservation rejected(
			UUID orderId,
			UUID productId,
			Integer quantity,
			String failureMessage
	) {
		InventoryReservation inventoryReservation = new InventoryReservation();
		
		inventoryReservation.status = InventoryStatus.REJECTED;
		
		inventoryReservation.orderId = orderId;
		inventoryReservation.productId = productId;
		inventoryReservation.quantity = quantity;
		
		inventoryReservation.failureMessage = failureMessage;
		
		return inventoryReservation;
	}
	
	public void confirm() {
		boolean valid = validateReserved();
		
		if(!valid) throw new IllegalStateException("주문을 확정할 수 있는 상태가 아닙니다.");
		
		this.status = InventoryStatus.CONFIRMED;
	}
	
	public void released() {
		boolean valid = validateReserved();
		
		if(!valid) throw new IllegalStateException("주문을 해제할 수 있는 상태가 아닙니다.");
		
		this.status = InventoryStatus.RELEASED;
	}
	
	private boolean validateReserved() {
		if(!Objects.equals(this.status, InventoryStatus.RESERVED)) {
			return false;
		}
		
		return true;
	}
	
}
