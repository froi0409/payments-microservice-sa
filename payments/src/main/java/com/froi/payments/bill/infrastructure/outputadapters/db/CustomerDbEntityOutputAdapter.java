package com.froi.payments.bill.infrastructure.outputadapters.db;


import com.froi.payments.bill.infrastructure.outputports.CreateCustomerOutputPort;
import com.froi.payments.client.domain.Customer;
import com.froi.payments.common.PersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceAdapter
public class CustomerDbEntityOutputAdapter implements CreateCustomerOutputPort {

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
}
