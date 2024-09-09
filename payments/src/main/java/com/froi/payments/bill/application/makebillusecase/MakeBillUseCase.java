package com.froi.payments.bill.application.makebillusecase;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillDiscount;
import com.froi.payments.bill.domain.BillPaidType;
import com.froi.payments.bill.infrastructure.inputports.MakeBillInputPort;
import com.froi.payments.bill.infrastructure.outputadapters.BillDbEntityOutputAdapter;
import com.froi.payments.common.UseCase;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.outputadapters.db.CustomerDbEntityOutputAdapter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@UseCase
public class MakeBillUseCase implements MakeBillInputPort {

    private BillDbEntityOutputAdapter billDbEntityOutputAdapter;
    private CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter;

    public MakeBillUseCase (BillDbEntityOutputAdapter billDbEntityOutputAdapter, CustomerDbEntityOutputAdapter customerDbEntityOutputAdapter) {
        this.billDbEntityOutputAdapter = billDbEntityOutputAdapter;
        this.customerDbEntityOutputAdapter = customerDbEntityOutputAdapter;
    }

    @Override
    public byte[] makeHotelBill(MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException {
        Bill bill = MakeBillRequest.toDomain(makeBillRequest);
        bill.setPaidType(BillPaidType.HOTEL);
        return makeBill(bill, makeBillRequest);
    }

    @Override
    public byte[] makeRestaurantBill(MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException {
        Bill bill = MakeBillRequest.toDomain(makeBillRequest);
        bill.setPaidType(BillPaidType.RESTAURANT);
        return makeBill(bill, makeBillRequest);
    }

    @Override
    public byte[] makeBill(Bill bill, MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException {
        // verify if customer exists (we are assuming that the customer exists in SAT system)
        bill.setCustomer(checkCustomer(makeBillRequest));
        bill.setBillDate(LocalDateTime.now());
        checkPersonalSale(makeBillRequest.getCustomerNit(), bill.getBillDiscounts());
        bill.calculateSubTotal();
        bill.calculateTotal();
        billDbEntityOutputAdapter.makeBill(bill);
        return bill.generatePDF();
    }

    private Customer checkCustomer(MakeBillRequest makeBillRequest) throws InvalidSyntaxException {
        if (makeBillRequest.getCustomerNit() == null) {
            return null;
        }

        Optional<Customer> customerOptional = customerDbEntityOutputAdapter.findByNit(makeBillRequest.getCustomerNit());
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }

        Customer newCustomer = Customer.builder()
                .nit(makeBillRequest.getCustomerNit())
                .dpi(makeBillRequest.getOptionalCustomerDpi())
                .firstName(makeBillRequest.getOptionalCustomerFirstName())
                .lastName(makeBillRequest.getOptionalCustomerLastName())
                .birthDate(LocalDate.parse(makeBillRequest.getOptionalCustomerBirthDate()))
                .build();
        newCustomer.validateDpi();
        return customerDbEntityOutputAdapter.createCustomer(newCustomer);
    }

    private void checkPersonalSale(String nit, List<BillDiscount> billDiscounts) {
        // check if the bill is a personal sale
        if (nit == null) {
            return;
        }
    }

}
