package com.tmkcomputers.csms.bootstrap;

import com.tmkcomputers.csms.model.VehicleModel;
import com.tmkcomputers.csms.model.VehicleMake;
import com.tmkcomputers.csms.repository.VehicleModelRepository;
import com.tmkcomputers.csms.repository.VehicleMakeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@DependsOn("vehicleMakeSeeder")
@Component
public class VehicleModelSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleModelSeeder(VehicleModelRepository vehicleModelRepository,
            VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedVehicleModels();
    }

    private void seedVehicleModels() {
        if (vehicleModelRepository.count() == 0) {

            // Retrieve existing VehicleMakes from the repository by name
            VehicleMake tesla = vehicleMakeRepository.findByName("Tesla").orElseThrow();
            VehicleMake rivian = vehicleMakeRepository.findByName("Rivian").orElseThrow();
            VehicleMake lucid = vehicleMakeRepository.findByName("Lucid Motors").orElseThrow();
            VehicleMake nio = vehicleMakeRepository.findByName("NIO").orElseThrow();
            VehicleMake byd = vehicleMakeRepository.findByName("BYD").orElseThrow();
            VehicleMake porsche = vehicleMakeRepository.findByName("Porsche").orElseThrow();
            VehicleMake audi = vehicleMakeRepository.findByName("Audi").orElseThrow();
            VehicleMake ford = vehicleMakeRepository.findByName("Ford").orElseThrow();
            VehicleMake bmw = vehicleMakeRepository.findByName("BMW").orElseThrow();
            VehicleMake hyundai = vehicleMakeRepository.findByName("Hyundai").orElseThrow();

            // Create the list of VehicleModels and associate them with the correct
            // VehicleMake
            List<VehicleModel> vehicleModels = List.of(
                    new VehicleModel(null, "Model S", "Luxury sedan with long-range battery and autopilot features.",
                            tesla),
                    new VehicleModel(null, "Model 3", "Affordable electric sedan with impressive range and tech.",
                            tesla),
                    new VehicleModel(null, "R1T", "Electric adventure truck with off-road capabilities.", rivian),
                    new VehicleModel(null, "Lucid Air", "Luxury electric sedan with long-range and advanced tech.",
                            lucid),
                    new VehicleModel(null, "ES6", "Premium electric SUV from China, with battery swap tech.", nio),
                    new VehicleModel(null, "Tang", "Affordable electric SUV from BYD, available globally.", byd),
                    new VehicleModel(null, "Taycan", "High-performance electric sports sedan.", porsche),
                    new VehicleModel(null, "e-tron GT", "Luxury electric sedan with cutting-edge features.", audi),
                    new VehicleModel(null, "Mustang Mach-E", "Electric SUV from Ford with sporty performance.", ford),
                    new VehicleModel(null, "i4", "Luxury electric sedan from BMW with great performance.", bmw),
                    new VehicleModel(null, "Ioniq 5", "Futuristic electric SUV with fast charging.", hyundai));

            // Persist VehicleModels using the repository
            vehicleModelRepository.saveAll(vehicleModels);
        }
    }

}
