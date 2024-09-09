package com.froi.payments.bill.application.earmingsusecase;


import lombok.Value;

import java.time.LocalDate;

@Value
public class BookingCostsInfo {
    String bookingId;
    String bookingName;
    String date;
    Integer hotelId;
    String hotelName;
    String roomCode;
    Double maintenanceCost;
}
