package com.froi.payments.bill.application.customerconsumptionsusecase;

import lombok.Value;

@Value
public class CustomerRestaurantsInformation {
    String orderId;
    String restaurantName;
    String orderDate;
    String orderPaidDate;
    String orderName;
}
