package com.tmkcomputers.csms.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.service.ChargerOemService;
import com.tmkcomputers.csms.service.ExcelService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/charger-oems")
@Validated
public class ChargerOemController {

    private final ChargerOemService service;
    private final ExcelService excelService;

    public ChargerOemController(ChargerOemService service, ExcelService excelService) {
        this.excelService = excelService;
        this.service = service;
    }

    @GetMapping
    public List<ChargerOem> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargerOem> getById(@PathVariable Long id) {
        Optional<ChargerOem> chargerOem = service.findById(id);
        return chargerOem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ChargerOem> create(@Valid @RequestBody ChargerOem chargerOem) {
        ChargerOem savedOem = service.save(chargerOem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargerOem> update(@PathVariable Long id, @Valid @RequestBody ChargerOem chargerOem) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        chargerOem.setId(id);
        ChargerOem updatedOem = service.save(chargerOem);
        return ResponseEntity.ok(updatedOem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<ChargerOem> savedRecords = excelService.uploadFile(file);
            return ResponseEntity.ok(savedRecords);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Excel file.");
        }
    }

    @GetMapping("/download-excel")
    public ResponseEntity<InputStreamResource> downloadExcelFile() {
        try {
            ByteArrayInputStream in = excelService.generateExcelFile();

            // Define the headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=charger_oems.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType
                            .parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 if any exception occurs
        }
    }
}
