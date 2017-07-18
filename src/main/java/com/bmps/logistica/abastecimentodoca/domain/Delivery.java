package com.bmps.logistica.abastecimentodoca.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "delivery")
public class Delivery {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @NotNull
    @Column
    private Long vehicle;

    @OneToMany(mappedBy = "delivery", targetEntity = Delivery.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PackageDelivery> packages;

    public Long getVehicle() {
        return vehicle;
    }

    public void setVehicle(Long vehicle) {
        this.vehicle = vehicle;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public List<PackageDelivery> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDelivery> packages) {
        this.packages = packages;
    }
}
