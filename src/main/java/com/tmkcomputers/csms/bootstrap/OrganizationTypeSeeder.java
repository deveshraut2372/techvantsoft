package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.OrganizationType;
import com.tmkcomputers.csms.repository.OrganizationTypeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class OrganizationTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final OrganizationTypeRepository organizationTypeRepository;

    public OrganizationTypeSeeder(OrganizationTypeRepository organizationTypeRepository) {
        this.organizationTypeRepository = organizationTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedOrganizationTypes();
    }

    private void seedOrganizationTypes() {
        if (organizationTypeRepository.count() == 0) {
            List<OrganizationType> organizationTypes = Arrays.asList(
                new OrganizationType(null, "Hospital", "Hospitals and medical centers"),
                new OrganizationType(null, "Fuel Pump", "Petrol, diesel, CNG pumps"),
                new OrganizationType(null, "Hotels", "Hotels, resorts, resturants"),
                new OrganizationType(null, "Institutes", "Schools, colleges, universities")
            );

            organizationTypeRepository.saveAll(organizationTypes);
        }
    }
}

