package com.tmkcomputers.csms.service;

import java.util.List;
import java.util.Locale;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.AmenityDto;
import com.tmkcomputers.csms.dto.ChargingStationDto;
import com.tmkcomputers.csms.dto.ChargingStationResponseDto;
import com.tmkcomputers.csms.dto.DayOperationalHourDto;
import com.tmkcomputers.csms.dto.OperationalTimingDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;
import com.tmkcomputers.csms.model.Amenity;
import com.tmkcomputers.csms.model.FavoriteChargingStation;
import com.tmkcomputers.csms.model.OperationalHour;
import com.tmkcomputers.csms.model.Tenant;
import com.tmkcomputers.csms.model.ChargingStation;
import com.tmkcomputers.csms.repository.TenantRepository;
import com.tmkcomputers.csms.repository.FavoriteChargingStationRepository;
import com.tmkcomputers.csms.repository.ChargingStationRepository;
import com.tmkcomputers.csms.utlity.DistanceCalculator;

import jakarta.transaction.Transactional;

@Service
public class ChargingStationService {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private FavoriteChargingStationRepository favoriteChargingStationRepository;

    @Autowired
    private ChargingStationImageService imageService;

    public List<ChargingStation> getAllChargingStations() {
        return chargingStationRepository.findAll();
    }

    public List<ChargingStation> getChargingStationsByTenantId(Long tenantId) {
        return chargingStationRepository.findByTenantId(tenantId);
    }

    public ChargingStation getChargingStationEntity(Long id) {
        return chargingStationRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("ChargingStation", "id", id);
        });
    }

    public ChargingStationDto getChargingStationById(Long id) {
        var optionalChargingStation = chargingStationRepository.findById(id);

        if (optionalChargingStation.isPresent()) {
            var chargingStation = optionalChargingStation.get();
            var chargingStationDto = new ChargingStationDto();
            chargingStationDto.setId(chargingStation.getId());
            chargingStationDto.setName(chargingStation.getName());
            chargingStationDto.setDescription(chargingStation.getDescription());
            chargingStationDto.setAddress(chargingStation.getAddress());
            chargingStationDto.setPincode(chargingStation.getPincode());
            chargingStationDto.setLatitude(chargingStation.getLatitude());
            chargingStationDto.setLongitude(chargingStation.getLongitude());
            chargingStationDto.setOpen24x7(chargingStation.isOpen24x7());

            if (chargingStation.isOpen24x7()) {
                chargingStationDto.setOperationalHours(null);
            } else {
                List<DayOperationalHourDto> dailyOperationalHoursDTOList = new ArrayList<>();
                for (var operationalHour : chargingStation.getOperationalHours()) {
                    DayOperationalHourDto dayOperationalHoursDTO = new DayOperationalHourDto();
                    dayOperationalHoursDTO.setDay(operationalHour.getDay());
                    dayOperationalHoursDTO.setOpen(operationalHour.isOpen());

                    OperationalTimingDto timingsDTO = new OperationalTimingDto();
                    timingsDTO.setOpenTime(operationalHour.getOpenTime().toString());
                    timingsDTO.setCloseTime(operationalHour.getCloseTime().toString());

                    dayOperationalHoursDTO.setOperationalTiming(timingsDTO);
                    dailyOperationalHoursDTOList.add(dayOperationalHoursDTO);
                }

                chargingStationDto.setOperationalHours(dailyOperationalHoursDTOList);
            }

            var amenity = chargingStation.getAmenity();
            if (amenity != null) {
                AmenityDto amenityDTO = new AmenityDto(amenity);

                chargingStationDto.setAmenity(amenityDTO);
            }

            return chargingStationDto;
        }
        return null;
    }

    public ChargingStation createChargingStation(ChargingStationDto chargingStation) {
        return tenantRepository.findById(chargingStation.getTenantId()).map(tenant -> {
            ChargingStation newChargingStation = new ChargingStation();
            newChargingStation.setName(chargingStation.getName());
            newChargingStation.setDescription(chargingStation.getDescription());
            newChargingStation.setAddress(chargingStation.getAddress());
            newChargingStation.setPincode(chargingStation.getPincode());
            newChargingStation.setLatitude(chargingStation.getLatitude());
            newChargingStation.setLongitude(chargingStation.getLongitude());
            newChargingStation.setOpen24x7(true);
            newChargingStation.setTenant(tenant);
            return chargingStationRepository.save(newChargingStation);
        }).orElseThrow(() -> {
            throw new ResourceNotFoundException("Tenant", "id", chargingStation.getTenantId());
        });
    }

    public Optional<ChargingStation> updateChargingStation(Long id, ChargingStationDto chargingStationDetails) {
        return chargingStationRepository.findById(id)
                .map(chargingStation -> {

                    AmenityDto amenityDetails = chargingStationDetails.getAmenity();

                    if (amenityDetails != null) {
                        Amenity amenity = chargingStation.getAmenity();
                        if (amenity == null) {
                            amenity = new Amenity();
                        }
                        amenity.setWifi(amenityDetails.isWifi());
                        amenity.setFoodcourt(amenityDetails.isFoodcourt());
                        amenity.setWashroom(amenityDetails.isWashroom());
                        amenity.setChildren_playarea(amenityDetails.isChildren_playarea());
                        amenity.setAtm(amenityDetails.isAtm());
                        chargingStation.setAmenity(amenity);
                    }

                    chargingStation.setName(chargingStationDetails.getName());
                    chargingStation.setDescription(chargingStationDetails.getDescription());
                    chargingStation.setAddress(chargingStationDetails.getAddress());
                    chargingStation.setPincode(chargingStationDetails.getPincode());
                    chargingStation.setLatitude(chargingStationDetails.getLatitude());
                    chargingStation.setLongitude(chargingStationDetails.getLongitude());
                    chargingStation.setOpen(chargingStationDetails.isOpen());
                    chargingStation.setOpen24x7(chargingStationDetails.isOpen24x7());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);

                    if (chargingStationDetails.isOpen24x7()) {
                        var hoursToBeDeleted = chargingStation.getOperationalHours();
                        for (OperationalHour operationalHour : hoursToBeDeleted) {
                            operationalHour.setChargingStation(null);
                        }
                        chargingStation.getOperationalHours().clear();
                    } else {
                        var newOperationalHours = chargingStationDetails.getOperationalHours();
                        var existingOperationalHours = chargingStation.getOperationalHours();
                        if (existingOperationalHours == null || existingOperationalHours.size() == 0) {
                            List<OperationalHour> operationalHoursList = new ArrayList<>();
                            for (DayOperationalHourDto newOperationalHour : newOperationalHours) {
                                OperationalHour operationalHour = new OperationalHour();
                                operationalHour.setDay(newOperationalHour.getDay());
                                operationalHour.setOpen(newOperationalHour.isOpen());
                                operationalHour
                                        .setOpenTime(LocalTime
                                                .parse(newOperationalHour.getOperationalTiming().getOpenTime().trim(),
                                                        formatter));
                                operationalHour
                                        .setCloseTime(LocalTime
                                                .parse(newOperationalHour.getOperationalTiming().getCloseTime().trim(),
                                                        formatter));
                                operationalHour.setChargingStation(chargingStation);
                                operationalHoursList.add(operationalHour);
                            }
                            chargingStation.setOperationalHours(operationalHoursList);
                        } else {
                            for (OperationalHour operationalHour : existingOperationalHours) {
                                for (DayOperationalHourDto newOperationalHour : newOperationalHours) {
                                    if (operationalHour.getDay().equals(newOperationalHour.getDay())) {
                                        operationalHour.setOpen(newOperationalHour.isOpen());
                                        operationalHour
                                                .setOpenTime(LocalTime
                                                        .parse(newOperationalHour.getOperationalTiming()
                                                                .getOpenTime().trim(), formatter));
                                        operationalHour
                                                .setCloseTime(LocalTime
                                                        .parse(newOperationalHour.getOperationalTiming()
                                                                .getCloseTime().trim(), formatter));
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    return chargingStationRepository.save(chargingStation);
                });
    }

    public void deleteChargingStation(Long id) {
        chargingStationRepository.deleteById(id);
    }

    public List<ChargingStationResponseDto> findNearestChargingStations(double latitude, double longitude,
            double distanceThreshold) {
        List<ChargingStation> allChargingStations = chargingStationRepository.findAll();
        List<ChargingStationResponseDto> nearestChargingStations = new ArrayList<>();

        for (ChargingStation chargingStation : allChargingStations) {
            double distance = DistanceCalculator.calculateDistance(latitude, longitude, chargingStation.getLatitude(),
                    chargingStation.getLongitude());
            List<String> imageUrls = imageService.getChargingStationImages(chargingStation.getId());
            if (distance <= distanceThreshold) {
                var chargingStationDto = new ChargingStationResponseDto();
                chargingStationDto
                        .setDistance(new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP).doubleValue());
                chargingStationDto.setStationName(chargingStation.getName());
                chargingStationDto.setStationAddress(chargingStation.getAddress());
                chargingStationDto.setStationImage("charging_station2.png");
                chargingStationDto.setRating(4.5);
                chargingStationDto
                        .setTotalPoints(chargingStationRepository.getNumberOfConnectors(chargingStation.getId()));
                chargingStationDto.setOpen(chargingStation.isOpen());
                chargingStationDto.setId(chargingStation.getId());
                chargingStationDto.setLatitude(chargingStation.getLatitude());
                chargingStationDto.setLongitude(chargingStation.getLongitude());
                nearestChargingStations.add(chargingStationDto);
            }
        }
        Collections.sort(nearestChargingStations, Comparator.comparingDouble(ChargingStationResponseDto::getDistance));
        return nearestChargingStations;
    }

    private static final double EARTH_RADIUS = 6371.0; // Radius of the Earth in kilometers
    
    public List<ChargingStationResponseDto> parseJsonData(double latitude, double longitude, double radius, JSONArray stationsArray) {

        List<ChargingStation> stations = new ArrayList<>();

        for (int i = 0; i < stationsArray.length(); i++) {
            JSONObject stationObject = stationsArray.getJSONObject(i);

            var chargingStation = chargingStationRepository.findByName("Station " + stationObject.getLong("ID")).orElse(null);
            if (chargingStation == null) {
                // Create new charging station
                chargingStation = new ChargingStation();
                Tenant tenant = tenantRepository.findById(1L).orElse(null);
                if (tenant == null) {
                    return null;
                }
                chargingStation.setTenant(tenant);
                chargingStation.setOpen24x7(true);
                chargingStation.setId(stationObject.getLong("ID"));
                chargingStation.setName("Station " + stationObject.getLong("ID"));
                chargingStation.setDescription("");
            }

            if (!stationObject.isNull("Connections")) {
                JSONArray connectionsArray = stationObject.getJSONArray("Connections");
                chargingStation.setConnectionDetails(connectionsArray);
            }

            if (!stationObject.isNull("AddressInfo")) {
                JSONObject addressObject = stationObject.getJSONObject("AddressInfo");

                StringBuilder address = new StringBuilder();
                address.append(addressObject.optString("AddressLine1", ""));
                address.append(", ");
                address.append(addressObject.optString("Town", ""));
                address.append(", ");
                address.append(addressObject.optString("StateOrProvince", ""));
                address.append(", ");

                if (!addressObject.isNull("Country")) {
                    JSONObject countryObject = addressObject.getJSONObject("Country");
                    address.append(countryObject.optString("Title"));
                }

                chargingStation.setAddress(address.toString().replaceAll(", ,", ""));
                chargingStation.setLatitude(addressObject.optDouble("Latitude", 0.0));
                chargingStation.setLongitude(addressObject.optDouble("Longitude", 0.0));
                chargingStation.setPincode(addressObject.optString("Postcode", ""));
                chargingStation.setOpen(true);
            }

            Amenity amenity = chargingStation.getAmenity();
            if (amenity == null) {
                amenity = new Amenity();
                chargingStation.setAmenity(amenity);
            }
            stations.add(chargingStation);
        }

        var result = chargingStationRepository.saveAll(stations);
        List<ChargingStationResponseDto> nearestChargingStations = new ArrayList<>();

        for (ChargingStation chargingStation : result) {
            double distance = DistanceCalculator.calculateDistance(latitude, longitude, chargingStation.getLatitude(),
                    chargingStation.getLongitude());
            var chargingStationDto = new ChargingStationResponseDto();
                chargingStationDto
                        .setDistance(new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP).doubleValue());
                chargingStationDto.setStationName(chargingStation.getName());
                chargingStationDto.setStationAddress(chargingStation.getAddress());
                chargingStationDto.setStationImage("charging_station2.png");
                chargingStationDto.setRating(4.5);
                // chargingStationDto
                //         .setTotalPoints(chargingStationRepository.getNumberOfConnectors(chargingStation.getId()));

                var connectionsArray = chargingStation.getConnectionDetails();
                int totalPoints = 0;
                if (connectionsArray != null) {
                    for (int j = 0; j < connectionsArray.length(); j++) {
                        JSONObject connectionObject = connectionsArray.getJSONObject(j);
                        totalPoints += connectionObject.optInt("Quantity", 0);
                    }
                }
                chargingStationDto.setTotalPoints(totalPoints);

                chargingStationDto.setOpen(chargingStation.isOpen());
                chargingStationDto.setId(chargingStation.getId());
                chargingStationDto.setLatitude(chargingStation.getLatitude());
                chargingStationDto.setLongitude(chargingStation.getLongitude());
                nearestChargingStations.add(chargingStationDto);
        }
        Collections.sort(nearestChargingStations, Comparator.comparingDouble(ChargingStationResponseDto::getDistance));
        return nearestChargingStations;
    }

    private List<ChargingStation> generateChargingStations(double latitude, double longitude, double radius,
            boolean isOutsideRange, long tenantId) {
        List<ChargingStation> chargingStations = new ArrayList<>();
        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
        if (tenant == null) {
            return chargingStations;
        }

        Random random = new Random();
        double minLat = latitude - (radius / EARTH_RADIUS) * (180 / Math.PI);
        double maxLat = latitude + (radius / EARTH_RADIUS) * (180 / Math.PI);
        double minLon = longitude - (radius / EARTH_RADIUS) * (180 / Math.PI) / Math.cos(Math.toRadians(latitude));
        double maxLon = longitude + (radius / EARTH_RADIUS) * (180 / Math.PI) / Math.cos(Math.toRadians(latitude));

        for (int i = 0; i < 10; i++) {
            double chargingStationLatitude = random.nextDouble(minLat, maxLat);
            double chargingStationLongitude = random.nextDouble(minLon, maxLon);

            double distance = DistanceCalculator.calculateDistance(latitude, longitude, chargingStationLatitude,
                    chargingStationLongitude);

            // Decide to add chargingStation based on whether it's inside or outside the
            // radius
            if ((isOutsideRange && distance > radius) || (!isOutsideRange && distance <= radius)) {
                ChargingStation chargingStation = new ChargingStation();
                chargingStation.setName("Charging Station " + (i + 1));
                chargingStation.setAddress("Pune, India");
                chargingStation.setLatitude(chargingStationLatitude);
                chargingStation.setLongitude(chargingStationLongitude);
                chargingStation.setOpen(false);
                chargingStation.setTenant(tenant);

                Amenity amenity = new Amenity();
                amenity.setWifi(true);
                amenity.setFoodcourt(true);
                amenity.setWashroom(true);
                amenity.setChildren_playarea(true);
                amenity.setAtm(true);
                chargingStation.setAmenity(amenity);

                chargingStations.add(chargingStation);
            }
        }

        return chargingStations;
    }

    private List<ChargingStation> generateChargingStationsOutsideRange(double latitude, double longitude, double radius,
            long tenantId) {
        return generateChargingStations(latitude, longitude, radius, true, tenantId);
    }

    private List<ChargingStation> generateChargingStationsWithinRange(double latitude, double longitude, double radius,
            long tenantId) {
        return generateChargingStations(latitude, longitude, radius, false, tenantId);
    }

    public void seedChargingStations(Long tenantId) {
        List<ChargingStation> chargingStations = generateChargingStationsOutsideRange(18.5204, 73.8567, 10.0, tenantId);
        chargingStationRepository.saveAll(chargingStations);

        chargingStations = generateChargingStationsWithinRange(18.5204, 73.8567, 10.0, tenantId);
        chargingStationRepository.saveAll(chargingStations);
    }

    public List<ChargingStationResponseDto> getFavoriteChargingStations(Long userId, double latitude,
            double longitude) {
        var favoriteChargingStations = favoriteChargingStationRepository.findByUserId(userId);

        List<ChargingStationResponseDto> favouriteChargingStations = new ArrayList<>();

        for (var favoriteChargingStation : favoriteChargingStations) {
            var chargingStation = chargingStationRepository.findById(favoriteChargingStation.getChargingStationId())
                    .orElse(null);
            List<String> imageUrls = imageService.getChargingStationImages(chargingStation.getId());
            double distance = DistanceCalculator.calculateDistance(latitude, longitude, chargingStation.getLatitude(),
                    chargingStation.getLongitude());
            var chargingStationDto = new ChargingStationResponseDto();
            chargingStationDto
                    .setDistance(new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP).doubleValue());
            chargingStationDto.setStationName(chargingStation.getName());
            chargingStationDto.setStationAddress(chargingStation.getAddress());
            chargingStationDto.setLatitude(chargingStation.getLatitude());
            chargingStationDto.setLongitude(chargingStation.getLongitude());
            chargingStationDto.setStationImage("charging_station2.png");
            chargingStationDto.setRating(4.5);
            chargingStationDto
                    .setTotalPoints(chargingStationRepository.getNumberOfConnectors(chargingStation.getId()));
            chargingStationDto.setOpen(chargingStation.isOpen());
            chargingStationDto.setId(chargingStation.getId());
            favouriteChargingStations.add(chargingStationDto);
        }
        return favouriteChargingStations;
    }

    public boolean isFavorite(Long userId, Long chargingStationId) {
        var favoriteChargingStation = favoriteChargingStationRepository.findByUserIdAndChargingStationId(userId,
                chargingStationId);
        return favoriteChargingStation.isPresent();
    }

    public void addChargingStationToFavorites(Long userId, Long chargingStationId) {
        favoriteChargingStationRepository.save(new FavoriteChargingStation(userId, chargingStationId));
    }

    @Transactional
    public void removeChargingStationFromFavorites(Long userId, Long chargingStationId) {
        favoriteChargingStationRepository.deleteByUserIdAndChargingStationId(userId, chargingStationId);
    }
}
