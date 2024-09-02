package com.froi.payments.customer.infrastructure.outputports;

import com.froi.payments.customer.domain.Customer;

import java.util.Optional;

public interface FindCustomerOutputPort {
    Optional<Customer> findByNit(String nit);
}
