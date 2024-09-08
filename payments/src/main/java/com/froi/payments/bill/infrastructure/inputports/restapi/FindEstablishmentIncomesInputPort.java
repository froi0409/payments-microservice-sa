package com.froi.payments.bill.infrastructure.inputports.restapi;

import com.froi.payments.bill.infrastructure.inputadapters.restapi.FindEstablishmentIncomesResponse;

public interface FindEstablishmentIncomesInputPort {
    FindEstablishmentIncomesResponse findEstablishmentIncomes(String nit, String startDate, String endDate);
}
