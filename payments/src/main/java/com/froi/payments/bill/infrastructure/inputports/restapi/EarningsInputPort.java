package com.froi.payments.bill.infrastructure.inputports.restapi;

import com.froi.payments.bill.application.earmingsusecase.EarningsResponse;

public interface EarningsInputPort {
    EarningsResponse getEarningsReport();
}
