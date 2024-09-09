package com.froi.payments.bill.application.makebillusecase;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillDetail;
import com.froi.payments.bill.domain.BillDiscount;
import lombok.Value;

import java.util.List;

@Value
public class MakeBillRequest {
    String customerNit;
    String optionalCustomerDpi;
    String optionalCustomerFirstName;
    String optionalCustomerLastName;
    String optionalCustomerBirthDate;
    String establishmentId;
    String documentId;
    List<BillDetail> billDetails;
    List<BillDiscount> billDiscounts;
    Boolean hasDiscount;

    public static Bill toDomain(MakeBillRequest makeBillRequest) {
        return Bill.builder()
                .establishmentId(makeBillRequest.getEstablishmentId())
                .documentId(makeBillRequest.getDocumentId())
                .billDetails(makeBillRequest.getBillDetails())
                .billDiscounts(makeBillRequest.getBillDiscounts())
                .build();
    }
}
