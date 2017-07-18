package com.bmps.logistica.abastecimentodoca.service;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.Instrucao;
import com.bmps.logistica.abastecimentodoca.domain.PackageDelivery;
import com.bmps.logistica.abastecimentodoca.repository.DeliveryRepository;
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

    public List<Instrucao> consultarPassos(Long deliveryId, Long vehicleId) {
        log.info("Analisando passos para DeliveryId: {} e Vehicle: {}", deliveryId, vehicleId);

        Delivery delivery = deliveryRepository.findByDeliveryIdAndVehicle(deliveryId, vehicleId);

        return comporPassosParaAbastecimento(delivery);
    }

    private List<Instrucao> comporPassosParaAbastecimento(Delivery delivery) {
        Stack<PackageDelivery> abastecimento = new Stack<>();
        Stack<PackageDelivery> transferencia = new Stack<>();
        Stack<PackageDelivery> caminhao = new Stack<>();

        enriqueceAbastecimento(abastecimento, delivery);

        List<Instrucao> instrucoes = new LinkedList<>();
        sugereInstrucoesDeCarga(abastecimento.size(), abastecimento, caminhao, transferencia, instrucoes);

        return null;
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
     * Impelementação do algoritmo conhecido como Torre de Hanoi
     * @param n Número de elementos na área de transferencia
     * @param abastecimento
     * @param caminhao
     * @param transferencia
     * @param instrucoes
     * @return Uma lista de instruções para serem seguidas, passo a passo
     */
    private List<Instrucao> sugereInstrucoesDeCarga(int n, Stack<PackageDelivery> abastecimento, Stack<PackageDelivery> caminhao, Stack<PackageDelivery> transferencia, List<Instrucao> instrucoes) {
        Long step = 0L;
        if (n > 0) {
            sugereInstrucoesDeCarga(n-1, abastecimento, transferencia, caminhao, instrucoes);

            instrucoes.add(new Instrucao(step+1L, abastecimento.peek().getDelivery().getDeliveryId(), "zona de " + abastecimento, ""));
            caminhao.push(abastecimento.pop());
            System.out.println("---------------");
            System.out.println("Abastecimento: " + abastecimento);
            System.out.println("Caminhao: " + caminhao);
            System.out.println("Transferencia: " + transferencia);
            sugereInstrucoesDeCarga(n-1, transferencia, caminhao, abastecimento, instrucoes);
        }
        return instrucoes;
    }
}
