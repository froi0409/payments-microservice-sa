package com.froi.payments.bill.infrastructure.outputports;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.common.exceptions.IllegalEnumException;

public interface MakeBillOutputPort {
    Bill makeBill(Bill bill) throws IllegalEnumException;
}
