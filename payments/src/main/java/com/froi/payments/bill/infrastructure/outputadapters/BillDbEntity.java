package com.froi.payments.bill.infrastructure.outputadapters;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillPaidType;
import com.froi.payments.common.exceptions.IllegalEnumException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bill", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDbEntity {
    @Id
    @Column
    private String id;

    @Column(name = "paid_type")
    private Integer paidType;

    @Column(name = "establishment_id")
    private String establishmentId;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "total")
    private Double total;

    @Column(name = "bill_date")
    private LocalDateTime billDate;

    @Column
    private String customer;

    public Bill toDomain() throws IllegalEnumException {
        return Bill.builder()
                .id(UUID.fromString(id))
                .paidType(BillPaidType.fromOrdinal(paidType))
                .establishmentId(establishmentId)
                .documentId(documentId)
                .subTotal(subTotal)
                .total(total)
                .billDate(billDate)
                .build();
    }

    public static BillDbEntity fromDomain(Bill bill) {
        return new BillDbEntity(UUID.randomUUID().toString(),
                bill.getPaidType().ordinal(),
                bill.getEstablishmentId(),
                bill.getDocumentId(),
                bill.getSubTotal(),
                bill.getTotal(),
                bill.getBillDate(),
                bill.getCustomer().getNit());
    }

}
