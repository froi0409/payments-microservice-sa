package com.froi.payments.bill.infrastructure.inputports.restapi;

import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerConsumptionsResponse;
import com.froi.payments.common.exceptions.IllegalEnumException;

public interface FindCustomerConsumptionsInputPort {
    CustomerConsumptionsResponse findCustomerConsumptions(String customerNit, String startDate, String endDate, String establishmentId) throws IllegalEnumException;
}
