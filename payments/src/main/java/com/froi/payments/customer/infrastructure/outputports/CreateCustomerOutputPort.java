package com.froi.payments.customer.infrastructure.outputports;

import com.froi.payments.customer.domain.Customer;

public interface CreateCustomerOutputPort {
    Customer createCustomer(Customer customer);
}
