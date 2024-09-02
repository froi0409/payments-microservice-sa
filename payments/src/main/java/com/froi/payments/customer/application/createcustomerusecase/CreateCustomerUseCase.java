package com.froi.payments.customer.application.createcustomerusecase;

import com.froi.payments.common.UseCase;
import com.froi.payments.common.exceptions.DuplicatedEntityException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.outputadapters.db.CustomerDbEntityOutputAdapter;
import com.froi.payments.customer.infrastructure.inputports.CreateCustomerInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
public class CreateCustomerUseCase implements CreateCustomerInputPort {

    private CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter;

    @Autowired
    public CreateCustomerUseCase(CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter) {
        this.customerDbEntityOutputAdapter = customerDbEntityOutputAdapter;
    }

    @Override
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) throws DuplicatedEntityException, InvalidSyntaxException {
        if (customerDbEntityOutputAdapter.findByNit(createCustomerRequest.getNit()).isPresent()) {
            throw new DuplicatedEntityException(String.format("Customer with nit %s already exists", createCustomerRequest.getNit()));
        }
        Customer customer = createCustomerRequest.toDomain();
        customer.validateDpi();
        return customerDbEntityOutputAdapter.createCustomer(customer);
    }
}
