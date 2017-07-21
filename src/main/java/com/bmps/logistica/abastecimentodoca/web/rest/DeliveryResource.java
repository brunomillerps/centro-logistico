package com.bmps.logistica.abastecimentodoca.web.rest;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.Instrucao;
import com.bmps.logistica.abastecimentodoca.repository.DeliveryRepository;
import com.bmps.logistica.abastecimentodoca.service.DeliveryService;
import com.bmps.logistica.abastecimentodoca.service.InstrucoesDeAbastecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DeliveryResource {

    @Autowired
    InstrucoesDeAbastecimentoService instrucoesDeAbastecimentoService;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    DeliveryService deliveryService;

    @PostMapping(path = "/delivery",
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity cadastroDeCargas(@Valid @RequestBody Delivery delivery) {
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return deliveryRepository.findByDeliveryIdAndVehicle(delivery.getDeliveryId(), delivery.getVehicle())
                .map(deliveryFound -> new ResponseEntity<>("Delivery already exists", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    deliveryService.criaCarga(delivery);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                });
    }

    @GetMapping("/delivery/{deliveryId}/{vehicle}/step")
    public ResponseEntity<List<Instrucao>> listaInstrucoes(@PathVariable("deliveryId") Long deliveryId, @PathVariable("vehicle") Long vehicleId) throws Exception {
        List<Instrucao> instrucaos = instrucoesDeAbastecimentoService.consultarPassos(deliveryId, vehicleId);
        return new ResponseEntity<>(instrucaos, HttpStatus.OK);
    }
}
