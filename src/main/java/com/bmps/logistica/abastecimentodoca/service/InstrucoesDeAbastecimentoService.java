package com.bmps.logistica.abastecimentodoca.service;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.Instrucao;
import com.bmps.logistica.abastecimentodoca.domain.PackageDelivery;
import com.bmps.logistica.abastecimentodoca.repository.DeliveryRepository;
import com.bmps.logistica.abastecimentodoca.repository.PackageDeliveryRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InstrucoesDeAbastecimentoService {

    private static final Logger log = LoggerFactory.getLogger(InstrucoesDeAbastecimentoService.class);

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PackageDeliveryRepository packageDeliveryRepository;

    public List<Instrucao> consultarPassos(Long deliveryId, Long vehicleId) throws Exception {
        log.info("Analisando passos para DeliveryId: {} e Vehicle: {}", deliveryId, vehicleId);
        Optional<Delivery> delivery = deliveryRepository.findByDeliveryIdAndVehicle(deliveryId, vehicleId);
        if (!delivery.isPresent())
            throw new NotFoundException(String.format("Delivery was not found by deliveryId/vehicleId %s/%s", deliveryId, vehicleId));

        return comporPassosParaAbastecimento(delivery.get());
    }

    private List<Instrucao> comporPassosParaAbastecimento(Delivery delivery) {
        log.debug("Delivery Id: {}", delivery.getId());
        log.debug("Pacotes: {}", packageDeliveryRepository.findByDeliveryId(delivery.getId()));

        Stack<PackageDelivery> abastecimento = new Stack<>();
        Stack<PackageDelivery> transferencia = new Stack<>();
        Stack<PackageDelivery> caminhao = new Stack<>();

        log.info("Inicializando fila de abastecimento...");
        enriqueceAbastecimento(abastecimento, delivery);

        List<Instrucao> instrucoes = new LinkedList<>();
        log.info("Inicializando instruções...");
        sugereInstrucoesDeCarga(abastecimento.size(), abastecimento, caminhao, transferencia, instrucoes,
                Instrucao.ABASTECIMENTO, Instrucao.TRANSFERENCIA, Instrucao.CAMINHAO);

        log.debug("Instruções recuperadas: " + instrucoes.toString());
        return instrucoes;
    }

    private void enriqueceAbastecimento(Stack<PackageDelivery> abastecimento, Delivery delivery) {
        delivery.getPackages()
                .stream()
                .filter(Objects::nonNull)
                .sorted((o1, o2) -> o2.getWeight().compareTo(o1.getWeight()))
                .collect(Collectors.toList())
                .forEach(packageDelivery -> abastecimento.push(packageDelivery));
        log.debug("Pilha de abastecimento enriquecida {} ", abastecimento);
    }

    /**
     * Impelementação do algoritmo (torre de hanoi) que irá sugerir a ordem que os pacotes devem ser empilhados
     * @param n Número de elementos na área de transferencia
     * @param abastecimento
     * @param caminhao
     * @param transferencia
     * @param instrucoes
     * @param A
     * @param T
     * @param C
     * @return Uma lista de instruções para serem seguidas, passo a passo
     * */
    private void sugereInstrucoesDeCarga(int n, Stack<PackageDelivery> abastecimento, Stack<PackageDelivery> transferencia, Stack<PackageDelivery> caminhao,
                                         List<Instrucao> instrucoes, String A, String T, String C) {
        if (n > 0) {
            sugereInstrucoesDeCarga(n-1, abastecimento, caminhao, transferencia, instrucoes, A, C, T); // abastecimento para transferencia
            instrucoes.add(new Instrucao(getStep(instrucoes), abastecimento.peek().getId(), A, C)); // log
            caminhao.push(abastecimento.pop());  // movimento do abastecimento para o caminhão
            sugereInstrucoesDeCarga(n-1, transferencia, abastecimento, caminhao, instrucoes, T, A, C); // abastecimento para caminhao
        }
    }

    private Long getStep(List<Instrucao> instrucoes) {
        if (instrucoes.isEmpty()) return 1L;
        return instrucoes.get(instrucoes.size()-1).getStep()+1L;
    }
}