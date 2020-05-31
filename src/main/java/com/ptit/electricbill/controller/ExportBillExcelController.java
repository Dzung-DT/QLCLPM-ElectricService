package com.ptit.electricbill.controller;

import com.ptit.electricbill.config.ExcelGenerator;
import com.ptit.electricbill.model.HoaDonExcel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class ExportBillExcelController {

    List<HoaDonExcel> hoaDonExcels;

    @PostMapping("/export-bill-excel/send-data")
    @ResponseBody
    public ResponseEntity<String> exportBllExcel(@RequestBody List<HoaDonExcel> hoaDonExcelList) throws IOException {
        hoaDonExcels = hoaDonExcelList;
        System.out.println("Size: " + hoaDonExcels.size());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/download-bill-excel/bills.xlsx")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadBllExcel() throws IOException {
        ByteArrayInputStream in = ExcelGenerator.billsToExcel(hoaDonExcels);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bills.xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}

