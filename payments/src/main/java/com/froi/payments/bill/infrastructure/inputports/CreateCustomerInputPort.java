package com.froi.payments.bill.infrastructure.inputports;

import com.froi.payments.client.application.createcustomerusecase.CreateCustomerRequest;
import com.froi.payments.client.domain.Customer;

public interface CreateCustomerInputPort {
    Customer createCustomer(CreateCustomerRequest createCustomerRequest);
}
