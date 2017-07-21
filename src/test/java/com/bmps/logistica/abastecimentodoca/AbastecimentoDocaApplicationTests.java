package com.bmps.logistica.abastecimentodoca;

import com.bmps.logistica.abastecimentodoca.domain.Delivery;
import com.bmps.logistica.abastecimentodoca.domain.Instrucao;
import com.bmps.logistica.abastecimentodoca.domain.PackageDelivery;
import com.bmps.logistica.abastecimentodoca.repository.PackageDeliveryRepository;
import com.bmps.logistica.abastecimentodoca.service.DeliveryService;
import com.bmps.logistica.abastecimentodoca.service.InstrucoesDeAbastecimentoService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbastecimentoDocaApplicationTests {

	@Autowired
	DeliveryService deliveryService;

	@Autowired
	PackageDeliveryRepository packageDeliveryRepository;

	@Autowired
	InstrucoesDeAbastecimentoService instrucoesDeAbastecimentoService;

	@Test
	public void testInstrucoes() throws Exception {
		Delivery delivery = new Delivery();
		Long deliveryId = Long.valueOf(LocalDateTime.now().getNano());
		System.out.printf("Delivery Id is %s", deliveryId);
		delivery.setDeliveryId(deliveryId);
		Long vehicleId = Long.valueOf(LocalDateTime.now().getNano());
		delivery.setVehicle(vehicleId);
		System.out.printf("Vehicle Id is %s", vehicleId);

		delivery.setPackages(montarPacotes(delivery));

		deliveryService.criaCarga(delivery);

		List<Instrucao> instrucaos = instrucoesDeAbastecimentoService.consultarPassos(delivery.getDeliveryId(), delivery.getDeliveryId());

		instrucaos.forEach(instrucao -> System.out.println(instrucao));

		Assert.assertEquals(instrucaos.size(), 15);
		// TODO comparar exatamente o JSON experado
	}

	@Test
	public void erro() throws Exception {
		try {
			instrucoesDeAbastecimentoService.consultarPassos(333L, 333l);

		} catch (NotFoundException e) {
			Assert.assertEquals(e.getMessage(), "Delivery was not found by deliveryId/vehicleId 333/333");
		}

	}

	private List<PackageDelivery> montarPacotes(Delivery delivery) {

		List<PackageDelivery> packages = new ArrayList<>();
		PackageDelivery package1 = new PackageDelivery();
		package1.setWeight(new BigDecimal(100));
		package1.setDelivery(delivery);
		package1.setId(Long.valueOf(LocalDateTime.now().getNano()));

		PackageDelivery package2 = new PackageDelivery();
		package2.setWeight(new BigDecimal(200));
		package2.setId(Long.valueOf(LocalDateTime.now().getNano()));
		package2.setDelivery(delivery);

		PackageDelivery package3 = new PackageDelivery();
		package3.setWeight(new BigDecimal(300));
		package3.setDelivery(delivery);
		package3.setId(Long.valueOf(LocalDateTime.now().getNano()));

		PackageDelivery package4 = new PackageDelivery();
		package4.setWeight(new BigDecimal(400));
		package4.setId(Long.valueOf(LocalDateTime.now().getNano()));
		package4.setDelivery(delivery);

		packages.add(package1);
		packages.add(package2);
		packages.add(package3);
		packages.add(package4);

		return packages;
	}
}
