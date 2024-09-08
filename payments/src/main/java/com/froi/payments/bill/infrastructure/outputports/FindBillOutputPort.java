package com.froi.payments.bill.infrastructure.outputports;

import java.util.List;

public interface FindBillOutputPort {
    List<String> findCustomersWithTheMostBills();
}
