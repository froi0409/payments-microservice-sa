package com.froi.payments.bill.infrastructure.inputports;

import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.domain.Bill;
import com.froi.payments.common.exceptions.IllegalEnumException;

import java.io.ByteArrayOutputStream;

public interface MakeBillInputPort {
    byte[] makeHotelBill(MakeBillRequest makeBillRequest) throws IllegalEnumException;

    byte[] makeRestaurantBill(MakeBillRequest makeBillRequest) throws IllegalEnumException;

    byte[] makeBill(Bill bill, MakeBillRequest makeBillRequest) throws IllegalEnumException;
}
