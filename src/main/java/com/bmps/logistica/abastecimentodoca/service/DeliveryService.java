package com.bmps.logistica.abastecimentodoca.service;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.PackageDelivery;
import com.bmps.logistica.abastecimentodoca.repository.DeliveryRepository;
import com.bmps.logistica.abastecimentodoca.repository.PackageDeliveryRepository;
import jersey.repackaged.com.google.common.base.Preconditions;
import jersey.repackaged.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmps on 20/07/17.
 */
@Transactional
@Service
public class DeliveryService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    PackageDeliveryRepository packageDeliveryRepository;

    public void criaCarga(Delivery delivery) {
        Preconditions.checkNotNull(delivery.getPackages());
        Preconditions.checkNotNull(delivery.getDeliveryId());
        Preconditions.checkNotNull(delivery.getVehicle());
        log.info("Criando conjunto de cargas para DeliveryId/Vehicle {}/{}", delivery.getDeliveryId(), delivery.getVehicle());

        List<PackageDelivery> packagesNew = Lists.newArrayList(delivery.getPackages());

        delivery.setPackages(null);
        deliveryRepository.saveAndFlush(delivery);
        log.info("Carga criado com sucesso {}", delivery.getDeliveryId());

        enriquecePackages(delivery, packagesNew);
        List<PackageDelivery> packageDeliveryList = packageDeliveryRepository.save(delivery.getPackages());
        delivery.setPackages(packageDeliveryList);
        deliveryRepository.saveAndFlush(delivery);
    }

    private void enriquecePackages(Delivery delivery, List<PackageDelivery> packages) {
        List<PackageDelivery> packagesNew = new ArrayList<>();
        packages.forEach(packageDelivery -> {
                    packageDelivery.setDelivery(delivery);
                    packagesNew.add(packageDelivery);
                });
        delivery.setPackages(packagesNew);
    }
}
