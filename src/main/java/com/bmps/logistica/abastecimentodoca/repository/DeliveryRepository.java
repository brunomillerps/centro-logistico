package com.bmps.logistica.abastecimentodoca.repository;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByDeliveryIdAndVehicle(Long deliveryId, Long vehicle);
}
