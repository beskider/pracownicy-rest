package com.example.pracownicy.service;

import com.example.pracownicy.domain.Pracownik;
import com.example.pracownicy.repository.PracownicyRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PracownicyService {

    private final PracownicyRepository pracownicyRepository;

    @Autowired
    public PracownicyService(PracownicyRepository pracownicyRepository) {
        this.pracownicyRepository = pracownicyRepository;
    }

    public List<Pracownik> getAll() {
        return pracownicyRepository.findAll();
    }

    public Optional<Pracownik> findById(Long id) {
        return pracownicyRepository.findById(id);
    }

    public List<Pracownik> findByNazwa(String nazwa) {
        return pracownicyRepository.findByNazwa(nazwa);
    }

    public Pracownik save(Pracownik pracownik) {
        return pracownicyRepository.save(pracownik);
    }

    public void deleteById(Long id) {
        pracownicyRepository.deleteById(id);
    }

    public boolean createPdf(List<Pracownik> pracownicy, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if(!exists) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"pracownicy"+".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Pracownicy", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {0.5f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell id = new PdfPCell(new Paragraph("Id", tableHeader));
            id.setBorderColor(BaseColor.BLACK);
            id.setPaddingLeft(10);
            id.setHorizontalAlignment(Element.ALIGN_CENTER);
            id.setVerticalAlignment(Element.ALIGN_CENTER);
            id.setBackgroundColor(BaseColor.GRAY);
            id.setExtraParagraphSpace(5f);
            table.addCell(id);

            PdfPCell name = new PdfPCell(new Paragraph("Nazwa", tableHeader));
            name.setBorderColor(BaseColor.BLACK);
            name.setPaddingLeft(10);
            name.setHorizontalAlignment(Element.ALIGN_CENTER);
            name.setVerticalAlignment(Element.ALIGN_CENTER);
            name.setBackgroundColor(BaseColor.GRAY);
            name.setExtraParagraphSpace(5f);
            table.addCell(name);

            PdfPCell numerKontaktowy = new PdfPCell(new Paragraph("Numer kontaktowy", tableHeader));
            numerKontaktowy.setBorderColor(BaseColor.BLACK);
            numerKontaktowy.setPaddingLeft(10);
            numerKontaktowy.setHorizontalAlignment(Element.ALIGN_CENTER);
            numerKontaktowy.setVerticalAlignment(Element.ALIGN_CENTER);
            numerKontaktowy.setBackgroundColor(BaseColor.GRAY);
            numerKontaktowy.setExtraParagraphSpace(5f);
            table.addCell(numerKontaktowy);

            PdfPCell pensja = new PdfPCell(new Paragraph("Pensja", tableHeader));
            pensja.setBorderColor(BaseColor.BLACK);
            pensja.setPaddingLeft(10);
            pensja.setHorizontalAlignment(Element.ALIGN_CENTER);
            pensja.setVerticalAlignment(Element.ALIGN_CENTER);
            pensja.setBackgroundColor(BaseColor.GRAY);
            pensja.setExtraParagraphSpace(5f);
            table.addCell(pensja);

            for(Pracownik pracownik: pracownicy) {

                PdfPCell idValue = new PdfPCell(new Paragraph(String.valueOf(pracownik.getId()), tableBody));
                idValue.setBorderColor(BaseColor.BLACK);
                idValue.setPaddingLeft(10);
                idValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                idValue.setVerticalAlignment(Element.ALIGN_CENTER);
                idValue.setBackgroundColor(BaseColor.WHITE);
                idValue.setExtraParagraphSpace(5f);
                table.addCell(idValue);

                PdfPCell nameValue = new PdfPCell(new Paragraph(pracownik.getNazwa(), tableBody));
                nameValue.setBorderColor(BaseColor.BLACK);
                nameValue.setPaddingLeft(10);
                nameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nameValue.setVerticalAlignment(Element.ALIGN_CENTER);
                nameValue.setBackgroundColor(BaseColor.WHITE);
                nameValue.setExtraParagraphSpace(5f);
                table.addCell(nameValue);

                PdfPCell numerKontaktowyValue = new PdfPCell(new Paragraph(pracownik.getNumerKontaktowy(), tableBody));
                numerKontaktowyValue.setBorderColor(BaseColor.BLACK);
                numerKontaktowyValue.setPaddingLeft(10);
                numerKontaktowyValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                numerKontaktowyValue.setVerticalAlignment(Element.ALIGN_CENTER);
                numerKontaktowyValue.setBackgroundColor(BaseColor.WHITE);
                numerKontaktowyValue.setExtraParagraphSpace(5f);
                table.addCell(numerKontaktowyValue);


                String pensjaTxt = String.format ("%.2f zl", pracownik.getPensja());
                PdfPCell pensjaValue = new PdfPCell(new Paragraph(pensjaTxt, tableBody));
                pensjaValue.setBorderColor(BaseColor.BLACK);
                pensjaValue.setPaddingLeft(10);
                pensjaValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                pensjaValue.setVerticalAlignment(Element.ALIGN_CENTER);
                pensjaValue.setBackgroundColor(BaseColor.WHITE);
                pensjaValue.setExtraParagraphSpace(5f);
                table.addCell(pensjaValue);

            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        }

        catch(Exception e) {
            return false;
        }

    }

    public boolean createExcel(List<Pracownik> pracownicy, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if(!exists) {
            new File(filePath).mkdirs();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "pracownicy" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Pracownicy");
            workSheet.setDefaultColumnWidth(20);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell id = headerRow.createCell(0);
            id.setCellValue("Id");
            id.setCellStyle(headerCellStyle);

            HSSFCell name = headerRow.createCell(1);
            name.setCellValue("Nazwa");
            name.setCellStyle(headerCellStyle);

            HSSFCell numerKontaktowy = headerRow.createCell(2);
            numerKontaktowy.setCellValue("Numer kontaktowy");
            numerKontaktowy.setCellStyle(headerCellStyle);

            HSSFCell pensja = headerRow.createCell(3);
            pensja.setCellValue("Pensja");
            pensja.setCellStyle(headerCellStyle);

            int i = 1;

            for(Pracownik pracownik : pracownicy) {

                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell idValue = bodyRow.createCell(0);
                idValue.setCellValue(pracownik.getId());
                idValue.setCellStyle(bodyCellStyle);

                HSSFCell nameValue = bodyRow.createCell(1);
                nameValue.setCellValue(pracownik.getNazwa());
                nameValue.setCellStyle(bodyCellStyle);

                HSSFCell numerKontaktowyValue = bodyRow.createCell(2);
                numerKontaktowyValue.setCellValue(pracownik.getNumerKontaktowy());
                numerKontaktowyValue.setCellStyle(bodyCellStyle);

                HSSFCell pensjaValue = bodyRow.createCell(3);
                pensjaValue.setCellValue(pracownik.getPensja());
                pensjaValue.setCellStyle(bodyCellStyle);

                i++;

            }

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;

        }
        catch(Exception e) {
            return false;
        }

    }

}
