package com.froi.payments.bill.infrastructure.outputports.restapi;

import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerBookingsInformation;
import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerRestaurantsInformation;

public interface FindCustomerConsumptionsOutputPort {
    CustomerBookingsInformation findCustomerHotelConsumptions(String bookingId);
    CustomerRestaurantsInformation findCustomerRestaurants(String orderId);

}
