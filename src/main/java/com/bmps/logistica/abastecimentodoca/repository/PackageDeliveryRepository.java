package com.bmps.logistica.abastecimentodoca.repository;

import com.bmps.logistica.abastecimentodoca.domain.PackageDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageDeliveryRepository extends JpaRepository<PackageDelivery, Long> {

    List<PackageDelivery> findByDeliveryId(Long deliveryId);

}
