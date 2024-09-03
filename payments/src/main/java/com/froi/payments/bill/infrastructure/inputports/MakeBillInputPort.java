package com.froi.payments.bill.infrastructure.inputports;

import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.domain.Bill;
import com.froi.payments.common.exceptions.IllegalEnumException;

public interface MakeBillInputPort {
    void makeHotelBill(MakeBillRequest makeBillRequest) throws IllegalEnumException;

    void makeRestaurantBill(MakeBillRequest makeBillRequest) throws IllegalEnumException;

    void makeBill(Bill bill, MakeBillRequest makeBillRequest) throws IllegalEnumException;
}
