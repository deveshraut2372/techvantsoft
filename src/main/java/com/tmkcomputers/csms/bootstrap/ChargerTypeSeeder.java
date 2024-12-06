package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.ChargerType;
import com.tmkcomputers.csms.repository.ChargerTypeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class ChargerTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final ChargerTypeRepository chargerTypeRepository;

    public ChargerTypeSeeder(ChargerTypeRepository chargerTypeRepository) {
        this.chargerTypeRepository = chargerTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedChargerTypes();
    }

    private void seedChargerTypes() {
        if (chargerTypeRepository.count() == 0) {
            List<ChargerType> chargerTypes = Arrays.asList(
                new ChargerType(null, "Level 1", "Standard charger, 120V AC"),
                new ChargerType(null, "Level 2", "Faster charger, 240V AC"),
                new ChargerType(null, "DC Fast Charger", "High-speed charger, direct current"),
                new ChargerType(null, "Tesla Supercharger", "Tesla's proprietary high-speed charger")
            );

            chargerTypeRepository.saveAll(chargerTypes);
        }
    }
}

