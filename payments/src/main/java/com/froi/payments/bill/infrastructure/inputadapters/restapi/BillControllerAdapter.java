package com.froi.payments.bill.infrastructure.inputadapters.restapi;

import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerConsumptionsResponse;
import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.infrastructure.inputports.FindCustomerWithTheMostBillsInputPort;
import com.froi.payments.bill.infrastructure.inputports.MakeBillInputPort;
import com.froi.payments.bill.infrastructure.inputports.restapi.FindCustomerConsumptionsInputPort;
import com.froi.payments.bill.infrastructure.inputports.restapi.FindEstablishmentIncomesInputPort;
import com.froi.payments.common.WebAdapter;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/payments/v1/bills")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class BillControllerAdapter {
    private MakeBillInputPort makeBillInputPort;
    private FindCustomerWithTheMostBillsInputPort findCustomerWithTheMostBillsInputPort;
    private FindEstablishmentIncomesInputPort findEstablishmentIncomesInputPort;
    private FindCustomerConsumptionsInputPort findCustomerConsumptionsInputPort;

    @Autowired
    public BillControllerAdapter(MakeBillInputPort makeBillInputPort, FindCustomerWithTheMostBillsInputPort findCustomerWithTheMostBillsInputPort, FindEstablishmentIncomesInputPort findEstablishmentIncomesInputPort, FindCustomerConsumptionsInputPort findCustomerConsumptionsInputPort) {
        this.makeBillInputPort = makeBillInputPort;
        this.findCustomerWithTheMostBillsInputPort = findCustomerWithTheMostBillsInputPort;
        this.findEstablishmentIncomesInputPort = findEstablishmentIncomesInputPort;
        this.findCustomerConsumptionsInputPort = findCustomerConsumptionsInputPort;
    }

    @PostMapping("/hotel")
    @PreAuthorize("hasRole('HOTEL_EMPLOYEE')")
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
    @PreAuthorize("hasRole('RESTAURANT_EMPLOYEE')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FindEstablishmentIncomesResponse> findEstablishmentIncomes(@PathVariable String establishmentId, @PathVariable String startDate, @PathVariable String endDate) {
        FindEstablishmentIncomesResponse response = findEstablishmentIncomesInputPort.findEstablishmentIncomes(establishmentId, startDate, endDate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/findCustomerConsumptions/{customerNit}/{startDate}/{endDate}/{establishmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerConsumptionsResponse> findCustomerConsumptionsByEstablishment(@PathVariable String customerNit, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String establishmentId) throws IllegalEnumException {
        System.out.println("se va a empezar");
        CustomerConsumptionsResponse response = findCustomerConsumptionsInputPort.findCustomerConsumptions(customerNit, startDate, endDate, establishmentId);
        System.out.println("va a terminar");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/findCustomerConsumptions/{customerNit}/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerConsumptionsResponse> findCustomerConsumptions(@PathVariable String customerNit, @PathVariable String startDate, @PathVariable String endDate) throws IllegalEnumException {
        System.out.println("se va a empezar");
        CustomerConsumptionsResponse response = findCustomerConsumptionsInputPort.findCustomerConsumptions(customerNit, startDate, endDate, null);
        System.out.println("va a terminar");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
