package com.bmps.logistica.abastecimentodoca.service;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.Instrucao;
import com.bmps.logistica.abastecimentodoca.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Service
public class InstrucoesDeAbastecimentoDeCargaService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Instrucao> consultarPassos(Long deliveryId, Long vehicleId) {

        Delivery delivery = deliveryRepository.findByDeliveryIdAndVehicle(deliveryId, vehicleId);

        return comporPassosParaAbastecimento(delivery);
    }

    private List<Instrucao> comporPassosParaAbastecimento(Delivery delivery) {
        Stack<BigDecimal> abastecimento = new Stack<>();
        Stack<BigDecimal> transferencia = new Stack<>();
        Stack<BigDecimal> caminhao = new Stack<>();

        enriqueceAbastecimento(abastecimento, delivery);

        return null;
    }

    private void enriqueceAbastecimento(Stack<BigDecimal> abastecimento, Delivery delivery) {
        delivery.getPackages()
                .stream()
                .filter(Objects::nonNull)
                .map(packageDelivery -> abastecimento.push(packageDelivery.getWeight()));
    }

    private static void torreDeHanoi(int n, Stack<BigDecimal> abastecimento, Stack<BigDecimal> caminhao, Stack<BigDecimal> transferencia) {
        if (n > 0) {
            torreDeHanoi(n-1, abastecimento, transferencia, caminhao);
            caminhao.push(abastecimento.pop());
            System.out.println("---------------");
            System.out.println("Abastecimento: " + abastecimento);
            System.out.println("Caminhao: " + caminhao);
            System.out.println("Transferencia: " + transferencia);
            torreDeHanoi(n-1, transferencia, caminhao, abastecimento);
        }
    }
}
