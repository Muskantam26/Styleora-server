package com.example.controller;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) throws Exception {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Invoice"));
        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Username: " + order.getUsername()));
        document.add(new Paragraph("Status: " + order.getStatus()));
        document.add(new Paragraph("Total Amount: ₹" + order.getTotalAmount()));
        document.add(new Paragraph("Items:"));

        for (var item : order.getItems()) {
            document.add(new Paragraph(item.getProduct().getName() + " x " + item.getQuantity()));
        }

        document.close();

        byte[] pdfBytes = out.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + orderId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
