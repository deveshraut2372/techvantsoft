package com.tmkcomputers.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tmkcomputers.csms.model.ChargingStation;
import com.tmkcomputers.csms.model.ChargingStationImage;
import com.tmkcomputers.csms.repository.ChargingStationImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargingStationImageService {

    @Value("${server.address:http://localhost}")
    private String serverAddress;

    @Value("${server.port:8080}")
    private String serverPort;

    private final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ChargingStationImageRepository imageRepository;

    public void saveImage(ChargingStation chargingStation, MultipartFile file) throws IOException {
        // Generate unique file name using UUID
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save the file to the server
        Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        // Save image information in the database
        ChargingStationImage image = new ChargingStationImage();
        image.setChargingStation(chargingStation);
        image.setImageName(uniqueFileName);
        imageRepository.save(image);
    }

    public List<String> getChargingStationImages(Long chargingStationId) {
        List<ChargingStationImage> images = imageRepository.findByChargingStationId(chargingStationId);

        // Convert image names to URLs (assuming they are accessible via /uploads/)
        // Convert image names to full URLs
        return images.stream()
                .map(image -> serverAddress + ":" + serverPort + "/" + UPLOAD_DIR
                        + image.getImageName().replace("\\", "/"))
                .collect(Collectors.toList());
    }

}
