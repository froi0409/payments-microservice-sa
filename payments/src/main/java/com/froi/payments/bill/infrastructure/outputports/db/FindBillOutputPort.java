package com.froi.payments.bill.infrastructure.outputports.db;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.common.exceptions.IllegalEnumException;

import java.time.LocalDateTime;
import java.util.List;

public interface FindBillOutputPort {
    List<String> findCustomersWithTheMostBills();
    List<Bill> findCustomerBills(String customerNit, LocalDateTime start, LocalDateTime end, String establishmentId) throws IllegalEnumException;
    Double findTotalSpentByCustomerBetweenDates(String customerId, LocalDateTime start, LocalDateTime end, String establishmentId);
}
