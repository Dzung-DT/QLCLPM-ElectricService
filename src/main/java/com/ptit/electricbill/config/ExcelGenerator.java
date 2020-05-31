package com.ptit.electricbill.config;

import com.ptit.electricbill.model.HoaDonBill;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    public static ByteArrayInputStream billsToExcel(List<HoaDonBill> hoaDonExcels) throws IOException {
        String[] COLUMNs = {"STT", "Mã hóa đơn", "Mã tháng", "Mã khách hàng", "Tên", "Địa chỉ", "Số điện hiện tại", "Số điện tháng trước", "Số Kwh", "Loại điện", "Thuế", "Tiền(Đồng)", "Ngày tạo"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Bills");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;
            for (HoaDonBill hoaDon : hoaDonExcels) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(hoaDon.getStt());
                row.createCell(1).setCellValue(hoaDon.getMaHD());
                row.createCell(2).setCellValue(hoaDon.getMaThang());
                row.createCell(3).setCellValue(hoaDon.getMaKH());
                row.createCell(4).setCellValue(hoaDon.getTenKH());
                row.createCell(5).setCellValue(hoaDon.getDiaChi());
                row.createCell(6).setCellValue(hoaDon.getSoDienHienTai());
                row.createCell(7).setCellValue(hoaDon.getSoThangTruoc());
                row.createCell(8).setCellValue(hoaDon.getSoKwh());
                row.createCell(9).setCellValue(hoaDon.getLoaiDien());
                row.createCell(10).setCellValue(hoaDon.getThue());
                row.createCell(11).setCellValue(hoaDon.getTien());
                row.createCell(12).setCellValue(hoaDon.getNgayTao());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
