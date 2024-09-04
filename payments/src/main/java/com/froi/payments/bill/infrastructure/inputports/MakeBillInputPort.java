package com.froi.payments.bill.infrastructure.inputports;

import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.domain.Bill;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;

import java.io.ByteArrayOutputStream;

public interface MakeBillInputPort {
    byte[] makeHotelBill(MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException;

    byte[] makeRestaurantBill(MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException;

    byte[] makeBill(Bill bill, MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException;
}
