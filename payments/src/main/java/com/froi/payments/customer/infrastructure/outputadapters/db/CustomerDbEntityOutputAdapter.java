package com.froi.payments.customer.infrastructure.outputadapters.db;


import com.froi.payments.customer.infrastructure.outputports.CreateCustomerOutputPort;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.common.PersistenceAdapter;
import com.froi.payments.customer.infrastructure.outputports.FindCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PersistenceAdapter
public class CustomerDbEntityOutputAdapter implements CreateCustomerOutputPort, FindCustomerOutputPort {

    private CustomerDbEntityRepository customerDbEntityRepository;

    @Autowired
    public CustomerDbEntityOutputAdapter(CustomerDbEntityRepository customerDbEntityRepository) {
        this.customerDbEntityRepository = customerDbEntityRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerDbEntity dbEntity = CustomerDbEntity.fromDomain(customer);
        customerDbEntityRepository.save(dbEntity);
        return dbEntity.toDomain();
    }

    @Override
    public Optional<Customer> findByNit(String nit) {
        return customerDbEntityRepository.findById(nit)
                .map(CustomerDbEntity::toDomain);
    }


}
