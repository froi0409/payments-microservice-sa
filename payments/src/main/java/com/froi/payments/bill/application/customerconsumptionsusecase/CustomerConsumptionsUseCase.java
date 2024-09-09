package com.froi.payments.bill.application.customerconsumptionsusecase;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillPaidType;
import com.froi.payments.bill.infrastructure.inputports.restapi.FindCustomerConsumptionsInputPort;
import com.froi.payments.bill.infrastructure.outputadapters.BillDbEntityOutputAdapter;
import com.froi.payments.bill.infrastructure.outputadapters.restapi.FindConsumptionsRestAdapter;
import com.froi.payments.common.UseCase;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.outputadapters.db.CustomerDbEntityOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class CustomerConsumptionsUseCase implements FindCustomerConsumptionsInputPort {

    private FindConsumptionsRestAdapter findConsumptionsRestAdapter;
    private CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter;
    private BillDbEntityOutputAdapter billDbEntityOutputAdapter;

    @Autowired
    public CustomerConsumptionsUseCase(FindConsumptionsRestAdapter findConsumptionsRestAdapter, CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter, BillDbEntityOutputAdapter billDbEntityOutputAdapter) {
        this.findConsumptionsRestAdapter = findConsumptionsRestAdapter;
        this.customerDbEntityOutputAdapter = customerDbEntityOutputAdapter;
        this.billDbEntityOutputAdapter = billDbEntityOutputAdapter;
    }


    @Override
    public CustomerConsumptionsResponse findCustomerConsumptions(String customerNit, String startDate, String endDate) throws IllegalEnumException {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        Customer customer = customerDbEntityOutputAdapter.findByNit(customerNit)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with nit %s not found", customerNit)));
        Double totalSpent = billDbEntityOutputAdapter.findTotalSpentByCustomerBetweenDates(customer.getNit(), start, end);
        List<Bill> bills = billDbEntityOutputAdapter.findCustomerBills(customer.getNit(), start, end);

        List<CustomerBookingsInformation> customerBookingsInformation = findBookingsInformation(bills);
        List<CustomerRestaurantsInformation> customerRestaurantsInformation = new ArrayList<>();

        return CustomerConsumptionsResponse.builder()
                .customerNit(customer.getNit())
                .customerNane(customer.getLastName())
                .totalConsumption(totalSpent)
                .customerBookingsInformation(customerBookingsInformation)
                .customerRestaurantsInformation(customerRestaurantsInformation)
                .build();
    }

    private List<CustomerBookingsInformation> findBookingsInformation(List<Bill> bills) {
        List<CustomerBookingsInformation> bookingsInformationList = new ArrayList<CustomerBookingsInformation>();
        for (Bill bill : bills) {
            System.out.println("Document: " + bill.getDocumentId());
            if (bill.getDocumentId() != null && bill.getPaidType() == BillPaidType.HOTEL) {
                CustomerBookingsInformation bookingInformation = findConsumptionsRestAdapter.findCustomerHotelConsumptions(bill.getDocumentId());
                if (bookingInformation != null) {
                    System.out.println("Booking: " + bookingInformation.getBookingId());
                    bookingsInformationList.add(bookingInformation);
                    System.out.println("paso el booking");
                }
            }
        }
        return bookingsInformationList;
    }
}
