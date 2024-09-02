package com.froi.payments.bill.domain;

import com.froi.payments.common.DomainEntity;
import com.froi.payments.customer.domain.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private LocalDateTime billDate;
    private List<BillDetail> billDetails;
    private List<BillDiscount> billDiscounts;

    public void calculate() {
        calculateSubTotal();
        calculateTotal();
    }

    private void calculateSubTotal() {
        subTotal = 0;
        for (BillDetail billDetail : billDetails) {
            subTotal += billDetail.getAmount();
        }
    }

    private void calculateTotal() {
        total = subTotal;
        for (BillDiscount billDiscount : billDiscounts) {
            total -= billDiscount.getDiscounted();
        }
    }

}
