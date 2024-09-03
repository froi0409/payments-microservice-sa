package com.froi.payments.bill.domain;

import com.froi.payments.common.DomainEntity;
import com.froi.payments.customer.domain.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class Bill {
    private UUID id;
    private Customer customer;
    private String documentId;
    private double subTotal;
    private double total;
    private BillPaidType paidType;
    private LocalDateTime billDate;
    private List<BillDetail> billDetails;
    private List<BillDiscount> billDiscounts;

    public void calculateSubTotal() {
        subTotal = 0;
        for (BillDetail billDetail : billDetails) {
            subTotal += billDetail.getAmount();
        }
    }

    public void calculateTotal() {
        total = subTotal;
        for (BillDiscount billDiscount : billDiscounts) {
            total -= billDiscount.getDiscounted();
        }
    }


    public byte[] generatePDF() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String customerNit = this.customer.getNit() != null ? this.customer.getNit() : "C/F";

        try {
            // Header
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body>")
                    .append("<h1>Factura ID: ").append(this.id).append("</h1>")
                    .append("<p>Fecha: ").append(this.billDate).append("</p>")
                    .append("<p>Cliente: ").append(customerNit).append("</p>")
                    .append("<p>Total: ").append(this.total).append("</p>")
                    .append("<h2>Detalles de la Factura</h2>")
                    .append("<table border='1'>")
                    .append("<tr><th>Descripción</th><th>Monto</th></tr>");

            // Details
            for (BillDetail billDetail : billDetails) {
                htmlContent.append("<tr>")
                        .append("<td>").append(billDetail.getDescription()).append("</td>")
                        .append("<td>").append(billDetail.getAmount()).append("</td>")
                        .append("</tr>");
            }

            htmlContent.append("</table>");

            // Discounts
            htmlContent.append("<h2>Descuentos</h2>")
                    .append("<table border='1'>")
                    .append("<tr><th>Descripción</th><th>Descuento</th></tr>");

            for (BillDiscount billDiscount : billDiscounts) {
                htmlContent.append("<tr>")
                        .append("<td>").append(billDiscount.getDescription()).append("</td>")
                        .append("<td>").append(billDiscount.getDiscounted()).append("</td>")
                        .append("</tr>");
            }

            htmlContent.append("</table>")
                    .append("</body></html>");

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent.toString());
            renderer.layout();

            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
