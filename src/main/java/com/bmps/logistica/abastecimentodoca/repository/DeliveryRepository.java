package com.bmps.logistica.abastecimentodoca.repository;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByDeliveryIdAndVehicle(Long deliveryId, Long vehicle);
}
