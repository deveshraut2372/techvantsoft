package com.tmkcomputers.csms.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.repository.ChargerOemRepository;

@Service
public class ExcelService {
    @Autowired
    private ChargerOemRepository repository;

    public List<ChargerOem> uploadFile(MultipartFile file) throws IOException {
        List<ChargerOem> records = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0)
                continue; // Skip header row
            ChargerOem chargerOem = new ChargerOem();
            chargerOem.setName(row.getCell(0).getStringCellValue());
            chargerOem.setDescription(row.getCell(1).getStringCellValue());
            records.add(chargerOem);
        }
        workbook.close();

        return repository.saveAll(records);
    }

    public ByteArrayInputStream generateExcelFile() throws IOException {
        List<ChargerOem> oems = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ChargerOem");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "ID", "Name", "Description" };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Populate data rows
        int rowIdx = 1;
        for (ChargerOem oem : oems) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(oem.getId());
            row.createCell(1).setCellValue(oem.getName());
            row.createCell(2).setCellValue(oem.getDescription());
        }

        // Write to ByteArrayOutputStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
