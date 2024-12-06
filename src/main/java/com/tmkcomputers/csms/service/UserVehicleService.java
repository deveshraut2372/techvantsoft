package com.tmkcomputers.csms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmkcomputers.csms.model.UserVehicle;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.repository.UserVehicleRepository;
import com.tmkcomputers.csms.repository.ConnectorTypeRepository;
import com.tmkcomputers.csms.repository.VehicleModelRepository;
import com.tmkcomputers.csms.dto.CreateUserVehicleDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserVehicleService {

    @Autowired
    private UserVehicleRepository userVehicleRepository;

    @Autowired
    private ConnectorTypeRepository connectorTypeRepository;

    @Autowired
    private VehicleModelRepository vehicleModelRepository;

    public List<UserVehicle> findAllByUserId(Long userId) {
        return userVehicleRepository.findAllByUserId(userId);
    }

    public Optional<UserVehicle> findById(Long id) {
        return userVehicleRepository.findById(id);
    }

    public UserVehicle addUserVehicle(CreateUserVehicleDto dto, User user) {

        UserVehicle userVehicle = new UserVehicle();

        userVehicle.setConnectorType(connectorTypeRepository.findById(dto.getConnectorTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("ConnectorType", "id", dto.getConnectorTypeId()))
        );

        userVehicle.setVehicleModel(vehicleModelRepository.findById(dto.getVehicleModelId())
            .orElseThrow(() -> new ResourceNotFoundException("VehicleModel", "id", dto.getVehicleModelId()))
        );

        userVehicle.setRegistrationNumber(dto.getRegistrationNumber());
        userVehicle.setUser(user);
        return userVehicleRepository.save(userVehicle);
    }

    public UserVehicle updateUserVehicle(CreateUserVehicleDto dto, UserVehicle userVehicle) {
        if (dto.getConnectorTypeId() != userVehicle.getConnectorType().getId()) {
            userVehicle.setConnectorType(connectorTypeRepository.findById(dto.getConnectorTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ConnectorType", "id", dto.getConnectorTypeId()))
            );
        }

        if (dto.getVehicleModelId() != userVehicle.getVehicleModel().getId()) {
            userVehicle.setVehicleModel(vehicleModelRepository.findById(dto.getVehicleModelId())
                .orElseThrow(() -> new ResourceNotFoundException("VehicleModel", "id", dto.getVehicleModelId()))
            );
        }
        
        userVehicle.setRegistrationNumber(dto.getRegistrationNumber());
        return userVehicleRepository.save(userVehicle);
    }

    public void deleteById(Long id) {
        userVehicleRepository.deleteById(id);
    }
}

