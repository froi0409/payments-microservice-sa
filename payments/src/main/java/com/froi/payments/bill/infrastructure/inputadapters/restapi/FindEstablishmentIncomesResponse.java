package com.froi.payments.bill.infrastructure.inputadapters.restapi;

import com.froi.payments.bill.domain.Bill;
import lombok.Value;

import java.util.List;

@Value
public class FindEstablishmentIncomesResponse {
    Double totalIncomes;
    List<Bill> bills;
}
