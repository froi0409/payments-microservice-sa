package com.froi.payments.bill.application.customerconsumptionsusecase;

import lombok.Value;

@Value
public class CustomerBookingsInformation {
    String bookingId;
    String bookingDate;
    String bookingName;
    String bookingUser;
    String hotelId;
    String hotelName;
    String roomCode;
    String checkinExpectedDate;
    String checkoutExpectedDate;
    String checkinDate;
}
