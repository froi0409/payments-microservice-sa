package com.froi.payments.bill.infrastructure.outputports;

import com.froi.payments.client.domain.Customer;

public interface CreateCustomerOutputPort {
    Customer createCustomer(Customer customer);
}
