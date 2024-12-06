package com.tmkcomputers.csms.bootstrap;

import com.tmkcomputers.csms.model.ChargerModel;
import com.tmkcomputers.csms.model.ChargerType;
import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.repository.ChargerModelRepository;
import com.tmkcomputers.csms.repository.ChargerTypeRepository;
import com.tmkcomputers.csms.repository.ChargerOemRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DependsOn({"chargerTypeSeeder", "chargerOemSeeder"})
@Component
public class ChargerModelSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final ChargerModelRepository chargerModelRepository;
    private final ChargerTypeRepository chargerTypeRepository;
    private final ChargerOemRepository chargerOemRepository;

    public ChargerModelSeeder(ChargerModelRepository chargerModelRepository,
            ChargerTypeRepository chargerTypeRepository,
            ChargerOemRepository chargerOemRepository) {
        this.chargerModelRepository = chargerModelRepository;
        this.chargerTypeRepository = chargerTypeRepository;
        this.chargerOemRepository = chargerOemRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedChargerModels();
    }

    private void seedChargerModels() {
        if (chargerModelRepository.count() == 0) {

            // Fetching Charger Types and OEMs to establish foreign key relationships
            Optional<ChargerType> dcFastCharger = chargerTypeRepository.findByName("DC Fast Charger");
            Optional<ChargerType> level2Charger = chargerTypeRepository.findByName("Level 2");
            Optional<ChargerOem> tesla = chargerOemRepository.findByName("Tesla");
            Optional<ChargerOem> abb = chargerOemRepository.findByName("ABB");
            Optional<ChargerOem> chargePoint = chargerOemRepository.findByName("ChargePoint");
            Optional<ChargerOem> siemens = chargerOemRepository.findByName("Siemens");

            if (dcFastCharger.isPresent() && level2Charger.isPresent()) {
                List<ChargerModel> chargerModels = Arrays.asList(
                        // Tesla Supercharger models
                        new ChargerModel(null, "SCV3", "Supercharger V3",
                                new BigDecimal("250.00"), new BigDecimal("400.00"),
                                new BigDecimal("625.00"),
                                dcFastCharger.get(), tesla.get()),

                        new ChargerModel(null, "SCV2", "Supercharger V2",
                                new BigDecimal("150.00"), new BigDecimal("400.00"),
                                new BigDecimal("375.00"),
                                dcFastCharger.get(), tesla.get()),

                        // ABB Fast Charger
                        new ChargerModel(null, "Terra54", "Terra 54 DC Fast Charger",
                                new BigDecimal("50.00"), new BigDecimal("500.00"),
                                new BigDecimal("125.00"),
                                dcFastCharger.get(), abb.get()),

                        // ChargePoint Level 2 Charger
                        new ChargerModel(null, "CT4021", "ChargePoint Level 2 Charger",
                                new BigDecimal("7.20"), new BigDecimal("240.00"),
                                new BigDecimal("30.00"),
                                level2Charger.get(), chargePoint.get()),

                        // Siemens Level 2 Charger
                        new ChargerModel(null, "VC30GRYU", "VersiCharge Level 2",
                                new BigDecimal("9.60"), new BigDecimal("240.00"),
                                new BigDecimal("40.00"),
                                level2Charger.get(), siemens.get()));

                chargerModelRepository.saveAll(chargerModels);
            }
        }
    }
}
