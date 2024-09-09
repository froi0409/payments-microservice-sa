package com.froi.payments.bill.domain;

import com.froi.payments.common.DomainEntity;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class BillDetail {
    private String description;
    private double amount;

    public void validate() throws InvalidSyntaxException {
        if (amount < 0) {
            throw new InvalidSyntaxException("Amount must be a positive number");
        }
    }

}
