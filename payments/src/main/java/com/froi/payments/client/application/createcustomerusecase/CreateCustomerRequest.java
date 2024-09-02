package com.froi.payments.client.application.createcustomerusecase;

import com.froi.payments.client.domain.Customer;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateCustomerRequest {
    String nit;
    String dpi;
    String firstName;
    String lastName;
    String birthDate;

    public Customer toDomain() {
        return Customer.builder()
                .nit(nit)
                .dpi(dpi)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .build();
    }
}
