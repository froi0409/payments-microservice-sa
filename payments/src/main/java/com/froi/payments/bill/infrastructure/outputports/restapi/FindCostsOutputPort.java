package com.froi.payments.bill.infrastructure.outputports.restapi;

import com.froi.payments.bill.application.earmingsusecase.BookingCostsInfo;
import com.froi.payments.bill.application.earmingsusecase.EarningsResponse;
import com.froi.payments.bill.application.earmingsusecase.OrderCostsInfo;

import java.util.List;

public interface FindCostsOutputPort {
    List<BookingCostsInfo> findBookingCosts();
    List<OrderCostsInfo> findOrderCosts();

}
