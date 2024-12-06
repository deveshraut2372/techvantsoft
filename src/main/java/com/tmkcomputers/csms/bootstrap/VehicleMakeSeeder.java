package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.VehicleMake;
import com.tmkcomputers.csms.repository.VehicleMakeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class VehicleMakeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleMakeSeeder(VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedVehicleMakes();
    }

    private void seedVehicleMakes() {
        if (vehicleMakeRepository.count() == 0) {
            List<VehicleMake> vehicleMakes = Arrays.asList(
                    new VehicleMake(null, "Tesla",
                            "Pioneering American EV maker known for high-performance, long-range cars and autonomous driving. Models: S, 3, X, Y"),
                    new VehicleMake(null, "Rivian",
                            "US automaker focused on adventure EVs like the R1T and R1S, known for off-road capabilities and sustainability."),
                    new VehicleMake(null, "Lucid Motors",
                            "Luxury EV brand known for the Lucid Air, offering cutting-edge tech, long range, and premium design."),
                    new VehicleMake(null, "NIO",
                            "Chinese EV company known for advanced battery swapping tech and premium SUVs/sedans like ES6, ES8, ET7."),
                    new VehicleMake(null, "BYD",
                            "Chinese automaker producing affordable EVs, buses, and batteries, with models like the Tang and Han expanding globally."),
                    new VehicleMake(null, "Porsche",
                            "Luxury sports car maker Porsche's EV, the Taycan, offers high performance, cutting-edge tech, and premium design."),
                    new VehicleMake(null, "Audi",
                            "German automaker Audi produces luxury EVs like the e-tron and e-tron GT, focusing on premium features and innovation."),
                    new VehicleMake(null, "Ford",
                            "Iconic US brand with EV models like the Mustang Mach-E SUV and F-150 Lightning electric truck, focusing on performance and practicality."),
                    new VehicleMake(null, "BMW",
                            "BMW’s electric i series offers a balance of luxury and performance, with models like the i3, iX, and i4 focusing on tech and style."),
                    new VehicleMake(null, "Hyundai",
                            "Hyundai’s Ioniq series features futuristic design, fast charging, and affordability, with models like the Ioniq 5 and Kona Electric."));

            vehicleMakeRepository.saveAll(vehicleMakes);
        }
    }
}
