package com.froi.payments.customer.infrastructure.inputports;

import com.froi.payments.common.exceptions.DuplicatedEntityException;
import com.froi.payments.customer.application.createcustomerusecase.CreateCustomerRequest;
import com.froi.payments.customer.domain.Customer;

public interface CreateCustomerInputPort {
    Customer createCustomer(CreateCustomerRequest createCustomerRequest) throws DuplicatedEntityException;
}
