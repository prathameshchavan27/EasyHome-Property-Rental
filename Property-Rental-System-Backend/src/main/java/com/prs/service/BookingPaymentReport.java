//package com.prs.service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.prs.dao.BookingRepository;
//import com.prs.dto.BookingPaymentDTO;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@AllArgsConstructor
//@NoArgsConstructor
//@Slf4j
//public class BookingPaymentReport {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//    
//    // File will be stored on the Desktop (works on Mac and Windows)
//    private static final String FILE = System.getProperty("user.home") + "/Desktop/BookingPaymentStatement.pdf";
//
//    public List<BookingPaymentDTO> generateStatement() {
//        List<BookingPaymentDTO> bookingPayments = bookingRepository.getBookingPayments();
//        
//        Document document = new Document(PageSize.A4);
//        
//        try {
//            File file = new File(FILE);
//            File parentDir = file.getParentFile();
//            if (!parentDir.exists()) {
//                parentDir.mkdirs();
//            }
//            
//            try (FileOutputStream outputStream = new FileOutputStream(file)) {
//                PdfWriter.getInstance(document, outputStream);
//                document.open();
//
//                // Define fonts
//                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
//                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
//                Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.WHITE);
//                
//                // Title Table
//                PdfPTable titleTable = new PdfPTable(1);
//                titleTable.setWidthPercentage(100);
//                PdfPCell titleCell = new PdfPCell(new Phrase("Booking Report", headerFont));
//                titleCell.setBackgroundColor(new BaseColor(0, 102, 204));
//                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                titleCell.setPadding(8f);
//                titleTable.addCell(titleCell);
//                
//                // Data Table with 7 columns
//                PdfPTable table = new PdfPTable(7);
//                table.setWidthPercentage(100);
//                table.setWidths(new float[]{4, 4, 4, 2, 3, 4, 3});
//                
//                // Table Headers
//                String[] headers = {"Title", "Owner", "Tenant", "Booking ID", "Amount", "Transaction ID", "Status"};
//                for (String header : headers) {
//                    PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
//                    cell.setBackgroundColor(new BaseColor(0, 102, 204));
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setPadding(4f);
//                    table.addCell(cell);
//                }
//                
//                // Alternate row colors for readability
//                boolean alternate = false;
//                BaseColor rowColor1 = BaseColor.WHITE;
//                BaseColor rowColor2 = new BaseColor(224, 224, 224);
//                
//                for (BookingPaymentDTO bookingPayment : bookingPayments) {
//                    BaseColor rowColor = alternate ? rowColor2 : rowColor1;
//                    
//                    PdfPCell titleCellData = new PdfPCell(new Phrase(bookingPayment.getTitle(), normalFont));
//                    titleCellData.setBackgroundColor(rowColor);
//                    titleCellData.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(titleCellData);
//                    
//                    PdfPCell ownerCell = new PdfPCell(new Phrase(bookingPayment.getOwner(), normalFont));
//                    ownerCell.setBackgroundColor(rowColor);
//                    ownerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(ownerCell);
//                    
//                    PdfPCell tenantCell = new PdfPCell(new Phrase(bookingPayment.getTenant(), normalFont));
//                    tenantCell.setBackgroundColor(rowColor);
//                    tenantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(tenantCell);
//                    
//                    PdfPCell bookingIdCell = new PdfPCell(new Phrase(String.valueOf(bookingPayment.getBookingId()), normalFont));
//                    bookingIdCell.setBackgroundColor(rowColor);
//                    bookingIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(bookingIdCell);
//                    
//                    PdfPCell amountCell = new PdfPCell(new Phrase(bookingPayment.getAmount().toString(), normalFont));
//                    amountCell.setBackgroundColor(rowColor);
//                    amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(amountCell);
//                    
//                    PdfPCell transactionCell = new PdfPCell(new Phrase(bookingPayment.getTransactionId(), normalFont));
//                    transactionCell.setBackgroundColor(rowColor);
//                    transactionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(transactionCell);
//                    
//                    PdfPCell statusCell = new PdfPCell(new Phrase(bookingPayment.getStatus().toString(), normalFont));
//                    statusCell.setBackgroundColor(rowColor);
//                    statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(statusCell);
//                    
//                    alternate = !alternate;
//                }
//                
//                document.add(titleTable);
//                document.add(new Paragraph(" "));
//                document.add(table);
//                document.close();
//            }
//        } catch (IOException | DocumentException e) {
//            log.error("Error generating PDF: ", e);
//            throw new RuntimeException("Error generating PDF", e);
//        }
//        
//        return bookingPayments;
//    }
//}
package com.prs.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.prs.dao.BookingRepository;
import com.prs.dto.BookingPaymentDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BookingPaymentReport {

    @Autowired
    private BookingRepository bookingRepository;
    
    // File will be stored on the Desktop (works on Mac and Windows)
    private static final String FILE = System.getProperty("user.home") + "/src/main/resources/static/reports/";

    public List<BookingPaymentDTO> generateStatement() {
        List<BookingPaymentDTO> bookingPayments = bookingRepository.getBookingPayments();
        
        Document document = new Document(PageSize.A4);
        
        try {
            File file = new File(FILE);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                PdfWriter.getInstance(document, outputStream);
                document.open();

                // Define fonts
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
                Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.WHITE);
                
                // Title Table
                PdfPTable titleTable = new PdfPTable(1);
                titleTable.setWidthPercentage(100);
                PdfPCell titleCell = new PdfPCell(new Phrase("Booking Report", headerFont));
                titleCell.setBackgroundColor(new BaseColor(0, 102, 204));
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell.setPadding(8f);
                titleTable.addCell(titleCell);
                
                // Data Table with 7 columns
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{4, 4, 4, 2, 3, 4, 3});
                
                // Table Headers
                String[] headers = {"Title", "Owner", "Tenant", "Booking ID", "Amount", "Transaction ID", "Status"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
                    cell.setBackgroundColor(new BaseColor(0, 102, 204));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(4f);
                    table.addCell(cell);
                }
                
                // Alternate row colors for readability
                boolean alternate = false;
                BaseColor rowColor1 = BaseColor.WHITE;
                BaseColor rowColor2 = new BaseColor(224, 224, 224);
                
                // Accumulate total amount
                double totalAmount = 0.0;
                
                for (BookingPaymentDTO bookingPayment : bookingPayments) {
                    BaseColor rowColor = alternate ? rowColor2 : rowColor1;
                    
                    PdfPCell titleCellData = new PdfPCell(new Phrase(bookingPayment.getTitle(), normalFont));
                    titleCellData.setBackgroundColor(rowColor);
                    titleCellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(titleCellData);
                    
                    PdfPCell ownerCell = new PdfPCell(new Phrase(bookingPayment.getOwner(), normalFont));
                    ownerCell.setBackgroundColor(rowColor);
                    ownerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(ownerCell);
                    
                    PdfPCell tenantCell = new PdfPCell(new Phrase(bookingPayment.getTenant(), normalFont));
                    tenantCell.setBackgroundColor(rowColor);
                    tenantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(tenantCell);
                    
                    PdfPCell bookingIdCell = new PdfPCell(new Phrase(String.valueOf(bookingPayment.getBookingId()), normalFont));
                    bookingIdCell.setBackgroundColor(rowColor);
                    bookingIdCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(bookingIdCell);
                    
                    PdfPCell amountCell = new PdfPCell(new Phrase(bookingPayment.getAmount().toString(), normalFont));
                    amountCell.setBackgroundColor(rowColor);
                    amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(amountCell);
                    
                    PdfPCell transactionCell = new PdfPCell(new Phrase(bookingPayment.getTransactionId(), normalFont));
                    transactionCell.setBackgroundColor(rowColor);
                    transactionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(transactionCell);
                    
                    PdfPCell statusCell = new PdfPCell(new Phrase(bookingPayment.getStatus().toString(), normalFont));
                    statusCell.setBackgroundColor(rowColor);
                    statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(statusCell);
                    
                    alternate = !alternate;
                    
                    // Accumulate amount for total
                    totalAmount += bookingPayment.getAmount();
                }
                
                // Add Total Row (spanning the first 4 columns for label "Total", then display total amount in column 5, and two empty cells for columns 6 and 7)
                PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total", tableHeaderFont));
                totalLabelCell.setBackgroundColor(new BaseColor(0, 102, 204));
                totalLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                totalLabelCell.setPadding(4f);
                totalLabelCell.setColspan(4);
                table.addCell(totalLabelCell);
                
                PdfPCell totalAmountCell = new PdfPCell(new Phrase(String.valueOf(totalAmount), normalFont));
                totalAmountCell.setBackgroundColor(new BaseColor(224, 224, 224));
                totalAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(totalAmountCell);
                
                PdfPCell emptyCell1 = new PdfPCell(new Phrase("", normalFont));
                emptyCell1.setBackgroundColor(new BaseColor(224, 224, 224));
                table.addCell(emptyCell1);
                
                PdfPCell emptyCell2 = new PdfPCell(new Phrase("", normalFont));
                emptyCell2.setBackgroundColor(new BaseColor(224, 224, 224));
                table.addCell(emptyCell2);
                
                document.add(titleTable);
                document.add(new Paragraph(" ")); // Adding space
                document.add(table);
                document.close();
            }
        } catch (IOException | DocumentException e) {
            log.error("Error generating PDF: ", e);
            throw new RuntimeException("Error generating PDF", e);
        }
        
        return bookingPayments;
    }
}
