package com.froi.payments.bill.application.customerwiththemostbillsusecase;

import com.froi.payments.bill.infrastructure.inputports.FindCustomerWithTheMostBillsInputPort;
import com.froi.payments.bill.infrastructure.outputadapters.BillDbEntityOutputAdapter;
import com.froi.payments.common.UseCase;
import com.froi.payments.customer.infrastructure.outputadapters.db.CustomerDbEntityOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UseCase
public class CustomerWithTheMostBillsUseCase implements FindCustomerWithTheMostBillsInputPort {

    private CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter;
    private BillDbEntityOutputAdapter billDbEntityOutputAdapter;

    @Autowired
    public CustomerWithTheMostBillsUseCase(BillDbEntityOutputAdapter billDbEntityOutputAdapter, CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter) {
        this.billDbEntityOutputAdapter = billDbEntityOutputAdapter;
        this.customerDbEntityOutputAdapter = customerDbEntityOutputAdapter;
    }

    @Override
    public boolean findCustomerWithTheMostBills(String customerNit) {
        customerDbEntityOutputAdapter.findByNit(customerNit)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with nit %s not found", customerNit)));

        List<String> bestCustomersNits = billDbEntityOutputAdapter.findCustomersWithTheMostBills();
        return bestCustomersNits.contains(customerNit);
    }
}
