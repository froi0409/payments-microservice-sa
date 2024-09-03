package com.froi.payments.bill.infrastructure.inputadapters.restapi;

import com.froi.payments.bill.application.makebillusecase.MakeBillRequest;
import com.froi.payments.bill.infrastructure.inputports.MakeBillInputPort;
import com.froi.payments.common.WebAdapter;
import com.froi.payments.common.exceptions.IllegalEnumException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/payments/v1/bills")
@WebAdapter
public class BillControllerAdapter {
    private MakeBillInputPort makeBillInputPort;

    @Autowired
    public BillControllerAdapter(MakeBillInputPort makeBillInputPort) {
        this.makeBillInputPort = makeBillInputPort;
    }

    @PostMapping("/hotel")
    public ResponseEntity<byte[]>  makeHotelBill(@RequestBody MakeBillRequest makeBillRequest) throws IllegalEnumException {
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
    public ResponseEntity<byte[]>  makeRestaurantBill(@RequestBody MakeBillRequest makeBillRequest) throws IllegalEnumException {
        byte[] bill = makeBillInputPort.makeRestaurantBill(makeBillRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_hotel.pdf");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bill);
    }
}
