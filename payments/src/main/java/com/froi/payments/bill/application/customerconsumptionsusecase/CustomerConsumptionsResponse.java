package com.froi.payments.bill.application.customerconsumptionsusecase;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class CustomerConsumptionsResponse {
    String customerNit;
    String customerNane;
    Double totalConsumption;
    List<CustomerBookingsInformation> customerBookingsInformation;
    List<CustomerRestaurantsInformation> customerRestaurantsInformation;
}
