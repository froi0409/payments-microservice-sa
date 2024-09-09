package com.froi.payments.customer.infrastructure.inputadapters.restapi;

import com.froi.payments.common.WebAdapter;
import com.froi.payments.common.exceptions.DuplicatedEntityException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import com.froi.payments.customer.application.createcustomerusecase.CreateCustomerRequest;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.inputports.CreateCustomerInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/v1/customers")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class CustomerControllerAdapter {
    private CreateCustomerInputPort createCustomerInputPort;

    @Autowired
    public CustomerControllerAdapter(CreateCustomerInputPort createCustomerInputPort) {
        this.createCustomerInputPort = createCustomerInputPort;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) throws DuplicatedEntityException, InvalidSyntaxException {
        Customer customerCreated = createCustomerInputPort.createCustomer(createCustomerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomerResponse.fromDomain(customerCreated));
    }

}
