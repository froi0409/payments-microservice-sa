package com.froi.payments.bill.infrastructure.inputports;

public interface FindCustomerWithTheMostBillsInputPort {
    boolean findCustomerWithTheMostBills(String customerNit);
}
