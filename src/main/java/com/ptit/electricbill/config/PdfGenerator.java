package com.ptit.electricbill.config;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ptit.electricbill.model.HoaDonBill;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGenerator {
    public static ByteArrayInputStream billsToPdf(List<HoaDonBill> hoaDonPdfs) {
        String[] COLUMNs = {"STT", "Mã hóa đơn", "Mã tháng", "Mã khách hàng", "Tên", "Địa chỉ", "Số điện hiện tại", "Số điện tháng trước", "Số Kwh", "Loại điện", "Thuế", "Tiền(Đồng)", "Ngày tạo"};
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph para = new Paragraph("Bills Table", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            // Add PDF Table Header ->
            Stream.of(COLUMNs)
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (HoaDonBill h : hoaDonPdfs) {
                PdfPCell stt = new PdfPCell(new Phrase(h.getStt()));
                stt.setPaddingLeft(4);
                stt.setVerticalAlignment(Element.ALIGN_MIDDLE);
                stt.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(stt);

                PdfPCell maHD = new PdfPCell(new Phrase(h.getMaHD()));
                maHD.setPaddingLeft(4);
                maHD.setVerticalAlignment(Element.ALIGN_MIDDLE);
                maHD.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(maHD);

                PdfPCell maThang = new PdfPCell(new Phrase(h.getMaThang()));
                maThang.setVerticalAlignment(Element.ALIGN_MIDDLE);
                maThang.setHorizontalAlignment(Element.ALIGN_RIGHT);
                maThang.setPaddingRight(4);
                table.addCell(maThang);

                PdfPCell maKH = new PdfPCell(new Phrase(h.getMaKH()));
                maKH.setVerticalAlignment(Element.ALIGN_MIDDLE);
                maKH.setHorizontalAlignment(Element.ALIGN_RIGHT);
                maKH.setPaddingRight(4);
                table.addCell(maKH);

                PdfPCell tenKH = new PdfPCell(new Phrase(h.getTenKH()));
                tenKH.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tenKH.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tenKH.setPaddingRight(4);
                table.addCell(tenKH);

                PdfPCell diaChi = new PdfPCell(new Phrase(h.getDiaChi()));
                diaChi.setVerticalAlignment(Element.ALIGN_MIDDLE);
                diaChi.setHorizontalAlignment(Element.ALIGN_RIGHT);
                diaChi.setPaddingRight(4);
                table.addCell(diaChi);

                PdfPCell soDienHienTai = new PdfPCell(new Phrase(h.getSoDienHienTai()));
                soDienHienTai.setVerticalAlignment(Element.ALIGN_MIDDLE);
                soDienHienTai.setHorizontalAlignment(Element.ALIGN_RIGHT);
                soDienHienTai.setPaddingRight(4);
                table.addCell(soDienHienTai);

                PdfPCell soDienThangTruoc = new PdfPCell(new Phrase(h.getSoThangTruoc()));
                soDienThangTruoc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                soDienThangTruoc.setHorizontalAlignment(Element.ALIGN_RIGHT);
                soDienThangTruoc.setPaddingRight(4);
                table.addCell(soDienThangTruoc);

                PdfPCell soKwh = new PdfPCell(new Phrase(h.getSoKwh()));
                soKwh.setVerticalAlignment(Element.ALIGN_MIDDLE);
                soKwh.setHorizontalAlignment(Element.ALIGN_RIGHT);
                soKwh.setPaddingRight(4);
                table.addCell(soKwh);

                PdfPCell loaiDien = new PdfPCell(new Phrase(h.getLoaiDien()));
                loaiDien.setVerticalAlignment(Element.ALIGN_MIDDLE);
                loaiDien.setHorizontalAlignment(Element.ALIGN_RIGHT);
                loaiDien.setPaddingRight(4);
                table.addCell(loaiDien);

                PdfPCell thue = new PdfPCell(new Phrase(h.getThue()));
                thue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                thue.setHorizontalAlignment(Element.ALIGN_RIGHT);
                thue.setPaddingRight(4);
                table.addCell(thue);

                PdfPCell tien = new PdfPCell(new Phrase(h.getTien()));
                tien.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tien.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tien.setPaddingRight(4);
                table.addCell(tien);

                PdfPCell ngaoTao = new PdfPCell(new Phrase(h.getNgayTao()));
                ngaoTao.setVerticalAlignment(Element.ALIGN_MIDDLE);
                ngaoTao.setHorizontalAlignment(Element.ALIGN_RIGHT);
                ngaoTao.setPaddingRight(4);
                table.addCell(ngaoTao);
            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
