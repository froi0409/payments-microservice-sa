package com.froi.payments.client.domain;

import com.froi.payments.common.DomainEntity;
import com.froi.payments.common.exceptions.InvalidSyntaxException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@DomainEntity
public class Customer {
    private String nit;
    private String dpi;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public void validateDpi() throws InvalidSyntaxException {
        if (!StringUtils.isNumeric(dpi) || dpi.length() != 13) {
            throw new InvalidSyntaxException("DPI must be a 13-digit number");
        }
    }

}
