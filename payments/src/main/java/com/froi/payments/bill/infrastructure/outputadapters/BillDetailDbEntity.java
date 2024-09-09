package com.froi.payments.bill.infrastructure.outputadapters;

import com.froi.payments.bill.domain.BillDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "bill_detail", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String bill;

    @Column
    private String description;

    @Column
    private Double amount;

    public BillDetail toDomain() {
        return BillDetail.builder()
                .description(description)
                .amount(amount)
                .build();
    }

    public static BillDetailDbEntity fromDomain(BillDetail billDetail, String billId) {
        return new BillDetailDbEntity(UUID.randomUUID().toString(),
                billId,
                billDetail.getDescription(),
                billDetail.getAmount());
    }
}
