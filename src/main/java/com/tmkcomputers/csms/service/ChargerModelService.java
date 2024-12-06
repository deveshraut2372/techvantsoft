package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.dto.ChargerModelDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;
import com.tmkcomputers.csms.model.ChargerModel;
import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.model.ChargerType;
import com.tmkcomputers.csms.repository.ChargerModelRepository;
import com.tmkcomputers.csms.repository.ChargerOemRepository;
import com.tmkcomputers.csms.repository.ChargerTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ChargerModelService {

    private final ChargerModelRepository chargerModelRepository;
    private final ChargerTypeRepository chargerTypeRepository;
    private final ChargerOemRepository chargerOemRepository;

    public ChargerModelService(ChargerModelRepository chargerModelRepository,
            ChargerTypeRepository chargerTypeRepository,
            ChargerOemRepository chargerOemRepository) {
        this.chargerModelRepository = chargerModelRepository;
        this.chargerTypeRepository = chargerTypeRepository;
        this.chargerOemRepository = chargerOemRepository;
    }

    public List<ChargerModel> findAll() {
        return chargerModelRepository.findAll();
    }

    public Optional<ChargerModel> findById(Long id) {
        return chargerModelRepository.findById(id);
    }

    public ChargerModel createChargerModel(ChargerModelDto chargerModelDto) {

        ChargerModel chargerModel = new ChargerModel();
        chargerModel.setModelNumber(chargerModelDto.getModelNumber());
        chargerModel.setModelName(chargerModelDto.getModelName());
        chargerModel.setMaxPower(chargerModelDto.getMaxPower());
        chargerModel.setVoltage(chargerModelDto.getVoltage());
        chargerModel.setCurrent(chargerModelDto.getCurrent());

        ChargerType chargerType = chargerTypeRepository.findById(chargerModelDto.getChargerTypeId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("ChargerType", "id", chargerModelDto.getChargerTypeId());
                });

        ChargerOem chargerOem = chargerOemRepository.findById(chargerModelDto.getChargerOemId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("ChargerOem", "id", chargerModelDto.getChargerOemId());
                });

        chargerModel.setChargerType(chargerType);
        chargerModel.setChargerOem(chargerOem);

        return chargerModelRepository.save(chargerModel);
    }

    public Optional<ChargerModel> updateChargerModel(Long id, ChargerModelDto ChargerModelDetails) {
        return chargerModelRepository.findById(id)
                .map(chargerModel -> {
                    if (ChargerModelDetails.getChargerTypeId() != chargerModel.getChargerType().getId()) {
                        chargerTypeRepository.findById(ChargerModelDetails.getChargerTypeId())
                                .ifPresent(chargerModel::setChargerType);
                    }

                    if (ChargerModelDetails.getChargerOemId() != chargerModel.getChargerOem().getId()) {
                        chargerOemRepository.findById(ChargerModelDetails.getChargerOemId())
                                .ifPresent(chargerModel::setChargerOem);
                    }

                    chargerModel.setModelName(ChargerModelDetails.getModelName());
                    chargerModel.setModelNumber(ChargerModelDetails.getModelNumber());
                    chargerModel.setMaxPower(ChargerModelDetails.getMaxPower());
                    chargerModel.setVoltage(ChargerModelDetails.getVoltage());
                    chargerModel.setCurrent(ChargerModelDetails.getCurrent());
                    return chargerModelRepository.save(chargerModel);
                });
    }

    public void deleteById(Long id) {
        chargerModelRepository.deleteById(id);
    }

    public List<ChargerModel> getChargerModelsByChargerTypeAndChargerOem(Long chargerTypeId, Long chargerOemId) {
        return chargerModelRepository.findByChargerTypeIdAndChargerOemId(chargerTypeId, chargerOemId);
    }
}
