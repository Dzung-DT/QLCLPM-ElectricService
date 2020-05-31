package com.ptit.electricbill.controller;

import com.ptit.electricbill.config.PdfGenerator;
import com.ptit.electricbill.model.HoaDonBill;
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
public class ExportBillPdfController {
    List<HoaDonBill> hoaDonBills;

    @PostMapping("/export-bill-pdf/send-data")
    @ResponseBody
    public ResponseEntity<String> exportBillPdf(@RequestBody List<HoaDonBill> hoaDonPdfList) throws IOException {
        hoaDonBills = hoaDonPdfList;
        System.out.println("Size pdf " + hoaDonBills.size());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/download-bill-pdf/bills.pdf")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadBillPdf() throws IOException {
        ByteArrayInputStream in = PdfGenerator.billsToPdf(hoaDonBills);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bills.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
