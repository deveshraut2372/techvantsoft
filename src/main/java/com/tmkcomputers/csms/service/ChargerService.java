package com.tmkcomputers.csms.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.ChargerDto;
import com.tmkcomputers.csms.dto.ConnectorSummaryDto;
import com.tmkcomputers.csms.dto.SessionReviewDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;
import com.tmkcomputers.csms.model.Charger;
import com.tmkcomputers.csms.model.ChargerModel;
import com.tmkcomputers.csms.model.ChargingStation;
import com.tmkcomputers.csms.repository.ChargerModelRepository;
import com.tmkcomputers.csms.repository.ChargerRepository;
import com.tmkcomputers.csms.repository.ChargingStationRepository;

@Service
public class ChargerService {

    @Autowired
    private ChargerRepository chargerRepository;

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private ChargerModelRepository chargerModelRepository;

    public List<Charger> getAllChargers() {
        return chargerRepository.findAll();
    }

    public List<ConnectorSummaryDto> getConnectorsByChargingStationId(Long chargingStationId) {
        List<ConnectorSummaryDto> connectorInfoList = chargingStationRepository
                .findByChargingStationIdWithConnectors(chargingStationId);
        return connectorInfoList;
    }

    public List<SessionReviewDto> getCustomerReviewsByChargingStationId(Long chargingStationId) {
        List<SessionReviewDto> reviewList = chargingStationRepository
                .findByChargingStationIdWithReviews(chargingStationId);
        return reviewList;
    }

    public List<ChargerDto> getChargersByChargingStationId(Long chargingStationId) {
        var chargers = chargerRepository.findByChargingStationId(chargingStationId);
        var chargerDtos = new ArrayList<ChargerDto>();
        for (Charger charger : chargers) {
            var chargerDto = new ChargerDto();
            chargerDto.setId(charger.getId());
            chargerDto.setName(charger.getName());
            chargerDto.setDescription(charger.getDescription());
            chargerDto.setChargingStationId(charger.getChargingStation().getId());
            chargerDto.setChargerModelId(charger.getChargerModel().getId());
            chargerDto.setChargerOemId(charger.getChargerModel().getChargerOem().getId());
            chargerDto.setChargerTypeId(charger.getChargerModel().getChargerType().getId());
            chargerDtos.add(chargerDto);
        }

        return chargerDtos;
    }

    public Optional<Charger> getChargerById(Long id) {
        return chargerRepository.findById(id);
    }

    public Charger createCharger(ChargerDto chargerDto) {
        ChargingStation chargingStation = chargingStationRepository.findById(chargerDto.getChargingStationId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("ChargingStation", "id", chargerDto.getChargingStationId());
                });

        ChargerModel chargerModel = chargerModelRepository.findById(chargerDto.getChargerModelId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("ChargerModel", "id", chargerDto.getChargerModelId());
                });

        Charger newCharger = new Charger();
        newCharger.setName(chargerDto.getName());
        newCharger.setDescription(chargerDto.getDescription());
        newCharger.setChargingStation(chargingStation);
        newCharger.setChargerModel(chargerModel);

        return chargerRepository.save(newCharger);
    }

    public Optional<Charger> updateCharger(Long id, ChargerDto chargerDetails) {
        return chargerRepository.findById(id)
                .map(charger -> {

                    if (chargerDetails.getChargingStationId() != charger.getChargingStation().getId()) {
                        chargingStationRepository.findById(chargerDetails.getChargingStationId())
                                .ifPresent(charger::setChargingStation);
                    }

                    if (chargerDetails.getChargerModelId() != charger.getChargerModel().getId()) {
                        chargerModelRepository.findById(chargerDetails.getChargerModelId())
                                .ifPresent(charger::setChargerModel);
                    }

                    charger.setName(chargerDetails.getName());
                    charger.setDescription(chargerDetails.getDescription());
                    return chargerRepository.save(charger);
                });
    }

    public void deleteCharger(Long id) {
        chargerRepository.deleteById(id);
    }
}
