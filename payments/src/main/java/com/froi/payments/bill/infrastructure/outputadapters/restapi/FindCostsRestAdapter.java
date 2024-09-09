package com.froi.payments.bill.infrastructure.outputadapters.restapi;

import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerBookingsInformation;
import com.froi.payments.bill.application.earmingsusecase.BookingCostsInfo;
import com.froi.payments.bill.application.earmingsusecase.OrderCostsInfo;
import com.froi.payments.bill.infrastructure.outputports.restapi.FindCostsOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FindCostsRestAdapter implements FindCostsOutputPort {
    @Value("${hotels.url}")
    String hotelsUrl;
    @Value("${restaurants.url}")
    String restaurantsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public FindCostsRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BookingCostsInfo> findBookingCosts() {
        String url = hotelsUrl + "/v1/bookings/costs";
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<List<BookingCostsInfo>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<BookingCostsInfo>>() {}
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            System.err.println("Error (" + e.getStatusCode() + "): " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<OrderCostsInfo> findOrderCosts() {
        String url = restaurantsUrl + "/v1/orders/costs";
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<List<OrderCostsInfo>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<OrderCostsInfo>>() {}
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            System.err.println("Error (" + e.getStatusCode() + "): " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return null;
        }
        return null;
    }
}
