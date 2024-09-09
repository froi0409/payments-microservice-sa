package com.froi.payments.customer.infrastructure.inputadapters.restapi;

import com.froi.payments.customer.domain.Customer;
import lombok.Value;

@Value
public class CustomerResponse {
    private final String nit;
    private final String firstName;
    private final String lastName;

    public static CustomerResponse fromDomain(Customer customer) {
        return new CustomerResponse(customer.getNit(),
                customer.getFirstName(),
                customer.getLastName());
    }
}
