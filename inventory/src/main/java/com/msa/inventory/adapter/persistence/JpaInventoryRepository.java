package com.msa.inventory.adapter.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msa.inventory.domain.model.Inventory;

public interface JpaInventoryRepository extends JpaRepository<Inventory, UUID> {

	@Modifying
	@Query("""
			update Inventory i set i.stock = i.stock - :quantity
			where i.productId = :productId
					and i.stock >= :quantity
	""")
	int decreaseIfEnough(UUID productId, Integer quantity);
	
	
}
