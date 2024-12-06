package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.dto.VehicleModelDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;
import com.tmkcomputers.csms.model.VehicleModel;
import com.tmkcomputers.csms.model.VehicleMake;
import com.tmkcomputers.csms.repository.VehicleModelRepository;
import com.tmkcomputers.csms.repository.VehicleMakeRepository;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleModelService(VehicleModelRepository vehicleModelRepository,
            VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    public List<VehicleModel> findAll() {
        return vehicleModelRepository.findAll();
    }

    public Optional<VehicleModel> findById(Long id) {
        return vehicleModelRepository.findById(id);
    }

    public VehicleModel createVehicleModel(VehicleModelDto vehicleModelDto) {

        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName(vehicleModelDto.getName());
        vehicleModel.setDescription(vehicleModelDto.getDescription());

        VehicleMake vehicleMake = vehicleMakeRepository.findById(vehicleModelDto.getVehicleMakeId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("VehicleMake", "id", vehicleModelDto.getVehicleMakeId());
                });

        vehicleModel.setVehicleMake(vehicleMake);

        return vehicleModelRepository.save(vehicleModel);
    }

    public Optional<VehicleModel> updateVehicleModel(Long id, VehicleModelDto VehicleModelDetails) {
        return vehicleModelRepository.findById(id)
                .map(vehicleModel -> {

                    if (VehicleModelDetails.getVehicleMakeId() != vehicleModel.getVehicleMake().getId()) {
                        vehicleMakeRepository.findById(VehicleModelDetails.getVehicleMakeId())
                                .ifPresent(vehicleModel::setVehicleMake);
                    }

                    vehicleModel.setName(VehicleModelDetails.getName());
                    vehicleModel.setDescription(VehicleModelDetails.getDescription());
                    return vehicleModelRepository.save(vehicleModel);
                });
    }

    public void deleteById(Long id) {
        vehicleModelRepository.deleteById(id);
    }

    public List<VehicleModel> getVehicleModelsByVehicleMake(Long vehicleMakeId) {
        return vehicleModelRepository.findByVehicleMakeId(vehicleMakeId);
    }
}
