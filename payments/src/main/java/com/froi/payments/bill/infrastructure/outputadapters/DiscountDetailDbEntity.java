package com.froi.payments.bill.infrastructure.outputadapters;

import com.froi.payments.bill.domain.BillDiscount;
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
@Table(name = "discount_detail", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDetailDbEntity {
    @Id
    @Column
    private String id;

    @Column
    private String bill;

    @Column
    private String description;

    @Column
    private Double discounted;

    public BillDiscount toDomain() {
        return BillDiscount.builder()
                .description(description)
                .discounted(discounted)
                .build();
    }

    public static DiscountDetailDbEntity fromDomain(BillDiscount billDiscount, String billId) {
        return new DiscountDetailDbEntity(UUID.randomUUID().toString(),
                billId,
                billDiscount.getDescription(),
                billDiscount.getDiscounted());
    }
}
