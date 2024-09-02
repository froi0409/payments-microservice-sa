package com.froi.payments.bill.domain;

import com.froi.payments.common.exceptions.InvalidSyntaxException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillDiscount {
    private String description;
    private double discounted;

    private void validate() throws InvalidSyntaxException {
        if (discounted < 0) {
            throw new InvalidSyntaxException("Discounted must be a positive number");
        }
    }
}
