package com.froi.payments.bill.application.earmingsusecase;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class EarningsResponse {
    private double hotelEarnings;
    private double hotelCosts;
    private double restaurantEarnings;
    private double restaurantCosts;
    private double totalEarnings;
    private List<BookingCostsInfo> bookingCosts;
    private List<OrderCostsInfo> orderCosts;

}
