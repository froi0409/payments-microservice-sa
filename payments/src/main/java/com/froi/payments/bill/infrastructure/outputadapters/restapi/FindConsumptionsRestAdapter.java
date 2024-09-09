package com.froi.payments.bill.infrastructure.outputadapters.restapi;

import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerBookingsInformation;
import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerConsumptionsResponse;
import com.froi.payments.bill.application.customerconsumptionsusecase.CustomerRestaurantsInformation;
import com.froi.payments.bill.infrastructure.outputports.restapi.FindCustomerConsumptionsOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class FindConsumptionsRestAdapter implements FindCustomerConsumptionsOutputPort {
    @Value("${hotels.url}")
    String hotelsUrl;
    @Value("${restaurants.url}")
    String restaurantsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public FindConsumptionsRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CustomerBookingsInformation findCustomerHotelConsumptions(String bookingId) {
        String url = hotelsUrl + "/v1/bookings/" + bookingId;
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<CustomerBookingsInformation> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    CustomerBookingsInformation.class
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
    public CustomerRestaurantsInformation findCustomerRestaurants(String orderId) {
        return null;
    }

}
