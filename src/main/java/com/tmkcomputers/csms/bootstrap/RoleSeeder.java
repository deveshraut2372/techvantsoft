package com.tmkcomputers.csms.bootstrap;

import com.tmkcomputers.csms.model.Role;
import com.tmkcomputers.csms.model.RoleEnum;
import com.tmkcomputers.csms.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.ADMIN, RoleEnum.TENANT_MANAGER, RoleEnum.CHARGING_STATION_MANAGER, RoleEnum.USER };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.TENANT_MANAGER, "Tenant Manager role",
                RoleEnum.CHARGING_STATION_MANAGER, "ChargingStation Manager role",
                RoleEnum.USER, "User role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
