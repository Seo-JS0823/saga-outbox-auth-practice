package com.msa.inventory.adapter.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msa.inventory.domain.model.InventoryReservation;

public interface JpaInventoryReservationRepository extends JpaRepository<InventoryReservation, UUID> {

}
