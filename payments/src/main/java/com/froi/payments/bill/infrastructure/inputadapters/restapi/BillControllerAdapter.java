package com.froi.payments.bill.infrastructure.inputadapters.restapi;

import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.infrastructure.inputports.FindCustomerWithTheMostBillsInputPort;
import com.froi.payments.bill.infrastructure.inputports.MakeBillInputPort;
import com.froi.payments.bill.infrastructure.inputports.restapi.FindEstablishmentIncomesInputPort;
import com.froi.payments.common.WebAdapter;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/payments/v1/bills")
@WebAdapter
public class BillControllerAdapter {
    private MakeBillInputPort makeBillInputPort;
    private FindCustomerWithTheMostBillsInputPort findCustomerWithTheMostBillsInputPort;
    private FindEstablishmentIncomesInputPort findEstablishmentIncomesInputPort;

    @Autowired
    public BillControllerAdapter(MakeBillInputPort makeBillInputPort, FindCustomerWithTheMostBillsInputPort findCustomerWithTheMostBillsInputPort, FindEstablishmentIncomesInputPort findEstablishmentIncomesInputPort) {
        this.makeBillInputPort = makeBillInputPort;
        this.findCustomerWithTheMostBillsInputPort = findCustomerWithTheMostBillsInputPort;
        this.findEstablishmentIncomesInputPort = findEstablishmentIncomesInputPort;
    }

    @PostMapping("/hotel")
    public ResponseEntity<byte[]>  makeHotelBill(@RequestBody MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException {
        byte[] bill = makeBillInputPort.makeHotelBill(makeBillRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_hotel.pdf");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bill);
    }

    @PostMapping("/restaurant")
    public ResponseEntity<byte[]>  makeRestaurantBill(@RequestBody MakeBillRequest makeBillRequest) throws IllegalEnumException, InvalidSyntaxException {
        byte[] bill = makeBillInputPort.makeRestaurantBill(makeBillRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_hotel.pdf");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bill);
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/isOneOfTheBest/{customerNit}")
    public ResponseEntity<Void> findCustomerWithTheMostBills(@PathVariable String customerNit) {
        boolean customerWithTheMostBills = findCustomerWithTheMostBillsInputPort.findCustomerWithTheMostBills(customerNit);
        if (customerWithTheMostBills) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/establishmentIncomes/{establishmentId}/{startDate}/{endDate}")
    public ResponseEntity<FindEstablishmentIncomesResponse> findEstablishmentIncomes(@PathVariable String establishmentId, @PathVariable String startDate, @PathVariable String endDate) {
        FindEstablishmentIncomesResponse response = findEstablishmentIncomesInputPort.findEstablishmentIncomes(establishmentId, startDate, endDate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
