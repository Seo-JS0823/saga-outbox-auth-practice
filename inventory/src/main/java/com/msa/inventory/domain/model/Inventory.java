package com.msa.inventory.domain.model;

import java.util.UUID;

import com.msa.inventory.domain.request.InventoryCommandRequest.InventoryCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_inventory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID inventoryId;
	
	@Column(name = "product_id", nullable = false, unique = true)
	private UUID productId;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	public static Inventory create(InventoryCreateRequest inventoryCreateRequest) {
		Inventory inventory = new Inventory();
		
		inventory.productId = inventoryCreateRequest.productId();
		inventory.stock = inventoryCreateRequest.stock();
		
		return inventory;
	}
	
	public void decrease(Integer quantity) {
		if(this.stock - quantity < 0) {
			throw new IllegalArgumentException("재고가 부족하여 주문을 진행할 수 없습니다.");
		}
		
		this.stock = this.stock - quantity;
	}
	
	public void increase(Integer quantity) {
		this.stock = this.stock + quantity;
	}
	
}
