package com.example.partiallyimmutableentity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class Invoice {

    private final InvoiceId id; // Used by the system
    private final InvoiceNumber number; // Used to identify the invoice to the customer
    private final OrderId order;
    private final Instant invoiceDate;


    private PostalAddress billingAddress;
    private List<InvoiceItem> items;
    private LocalDate dueDate;
    private String yourReference;
    private String ourReference;
    private String notes;

    public Invoice(InvoiceId id, InvoiceNumber number, OrderId order, Instant invoiceDate) {
        this.id = id;
        this.number = number;
        this.order = order;
        this.invoiceDate = invoiceDate;
    }

    // Methods omitted for brevity
}
