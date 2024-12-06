package com.tmkcomputers.csms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.TenantDto;
import com.tmkcomputers.csms.model.Tenant;
import com.tmkcomputers.csms.repository.TenantRepository;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public List<TenantDto> findTenantChargingStationChargerStats() {
        return tenantRepository.findTenantChargingStationChargerStats();
    }

    public Optional<TenantDto> getTenantById(Long id) {
        return tenantRepository.findTenantChargingStationChargerStatsById(id);
    }

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> updateTenant(Long id, Tenant tenantDetails) {
        return tenantRepository.findById(id)
                .map(tenant -> {
                    tenant.setName(tenantDetails.getName());
                    tenant.setDescription(tenantDetails.getDescription());
                    tenant.setHeadOfficeAddress(tenantDetails.getHeadOfficeAddress());
                    return tenantRepository.save(tenant);
                });
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }
}
