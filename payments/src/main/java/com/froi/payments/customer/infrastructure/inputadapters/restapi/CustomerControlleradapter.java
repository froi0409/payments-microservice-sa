package com.froi.payments.customer.infrastructure.inputadapters.restapi;

import com.froi.payments.common.exceptions.DuplicatedEntityException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import com.froi.payments.customer.application.createcustomerusecase.CreateCustomerRequest;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.inputports.CreateCustomerInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/v1/customers")
public class CustomerControlleradapter {
    private CreateCustomerInputPort createCustomerInputPort;

    @Autowired
    public CustomerControlleradapter(CreateCustomerInputPort createCustomerInputPort) {
        this.createCustomerInputPort = createCustomerInputPort;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) throws DuplicatedEntityException, InvalidSyntaxException {
        Customer customerCreated = createCustomerInputPort.createCustomer(createCustomerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomerResponse.fromDomain(customerCreated));
    }

}
