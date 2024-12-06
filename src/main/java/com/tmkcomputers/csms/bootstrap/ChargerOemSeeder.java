package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.repository.ChargerOemRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class ChargerOemSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final ChargerOemRepository chargerOemRepository;

    public ChargerOemSeeder(ChargerOemRepository chargerOemRepository) {
        this.chargerOemRepository = chargerOemRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedChargerOems();
    }

    private void seedChargerOems() {
        if (chargerOemRepository.count() == 0) {
            List<ChargerOem> chargerOems = Arrays.asList(
                    new ChargerOem(null, "Tesla", "Manufacturer of Tesla Superchargers"),
                    new ChargerOem(null, "ABB", "Manufacturer of high-quality fast chargers"),
                    new ChargerOem(null, "Siemens", "Global leader in charging infrastructure"),
                    new ChargerOem(null, "Schneider Electric", "Manufacturer of residential and commercial chargers"),
                    new ChargerOem(null, "ChargePoint", "Leading provider of networked charging stations")
            );

            chargerOemRepository.saveAll(chargerOems);
        }
    }
}

