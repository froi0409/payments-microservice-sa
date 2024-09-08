package com.froi.payments.bill.infrastructure.outputports;

import com.froi.payments.bill.domain.Bill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FindEstablishmentIncomesOutputPort {
    List<Bill> findEstablishmentIncomes(String establishmentId, LocalDateTime startDate, LocalDateTime endDate);
}
