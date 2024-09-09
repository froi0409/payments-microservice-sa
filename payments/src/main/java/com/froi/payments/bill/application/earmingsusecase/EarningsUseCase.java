package com.froi.payments.bill.application.earmingsusecase;

import com.froi.payments.bill.infrastructure.inputports.restapi.EarningsInputPort;
import com.froi.payments.bill.infrastructure.outputadapters.BillDbEntityOutputAdapter;
import com.froi.payments.bill.infrastructure.outputadapters.restapi.FindCostsRestAdapter;
import com.froi.payments.common.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.List;

@UseCase
public class EarningsUseCase implements EarningsInputPort {

    private FindCostsRestAdapter findCostsRestAdapter;
    private BillDbEntityOutputAdapter billDbEntityOutputAdapter;

    @Autowired
    public EarningsUseCase(FindCostsRestAdapter findCostsRestAdapter, BillDbEntityOutputAdapter billDbEntityOutputAdapter) {
        this.findCostsRestAdapter = findCostsRestAdapter;
        this.billDbEntityOutputAdapter = billDbEntityOutputAdapter;
    }

    @Override
    public EarningsResponse getEarningsReport() {
        List<BookingCostsInfo> bookingCosts = findCostsRestAdapter.findBookingCosts();
        List<OrderCostsInfo> orderCosts = findCostsRestAdapter.findOrderCosts();

        DecimalFormat df = new DecimalFormat("#.00");

        double hotelEarnings = billDbEntityOutputAdapter.findTotalHotelEarnings();
        double restaurantEarnings = billDbEntityOutputAdapter.findTotalRestaurantEarnings();
        double hotelCosts = calculateTotalHotelsCosts(bookingCosts);
        double restaurantCosts = calculateTotalRestaurantsCosts(orderCosts);
        double totalEarnings = hotelEarnings + restaurantEarnings - hotelCosts - restaurantCosts;

        hotelEarnings = Double.parseDouble(df.format(hotelEarnings));
        restaurantEarnings = Double.parseDouble(df.format(restaurantEarnings));
        hotelCosts = Double.parseDouble(df.format(hotelCosts));
        restaurantCosts = Double.parseDouble(df.format(restaurantCosts));
        totalEarnings = Double.parseDouble(df.format(totalEarnings));

        return EarningsResponse.builder()
                .hotelEarnings(hotelEarnings)
                .hotelCosts(hotelCosts)
                .restaurantEarnings(restaurantEarnings)
                .restaurantCosts(restaurantCosts)
                .totalEarnings(totalEarnings)
                .bookingCosts(bookingCosts)
                .orderCosts(orderCosts)
                .build();
    }

    private double calculateTotalHotelsCosts(List<BookingCostsInfo> bookingCosts) {
        double totalCost = 0;
        for (BookingCostsInfo bookingCost : bookingCosts) {
             totalCost += bookingCost.getMaintenanceCost();
        }
        return totalCost;
    }

    private double calculateTotalRestaurantsCosts(List<OrderCostsInfo> orderCosts) {
        double totalCost = 0;
        for (OrderCostsInfo orderCost : orderCosts) {
            totalCost += orderCost.getDishCost();
        }
        return totalCost;
    }

}
