package com.froi.payments.bill.application.earmingsusecase;

import lombok.Value;

@Value
public class OrderCostsInfo {
    String orderId;
    String restaurantId;
    String restaurantName;
    String orderName;
    String dishId;
    String dishName;
    Double dishCost;
    String orderDate;
}
