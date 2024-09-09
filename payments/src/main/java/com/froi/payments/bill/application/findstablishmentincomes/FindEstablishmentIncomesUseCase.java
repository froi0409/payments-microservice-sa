package com.froi.payments.bill.application.findstablishmentincomes;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.infrastructure.inputadapters.restapi.FindEstablishmentIncomesResponse;
import com.froi.payments.bill.infrastructure.inputports.restapi.FindEstablishmentIncomesInputPort;
import com.froi.payments.bill.infrastructure.outputadapters.ReportBillsDbOutputAdapter;
import com.froi.payments.common.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class FindEstablishmentIncomesUseCase implements FindEstablishmentIncomesInputPort {

    private ReportBillsDbOutputAdapter reportBillsDbOutputAdapter;

    @Autowired
    public FindEstablishmentIncomesUseCase(ReportBillsDbOutputAdapter reportBillsDbOutputAdapter) {
        this.reportBillsDbOutputAdapter = reportBillsDbOutputAdapter;

    }

    @Override
    public FindEstablishmentIncomesResponse findEstablishmentIncomes(String nit, String startDate, String endDate) {
        LocalDateTime startDateParsed = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateParsed = LocalDateTime.parse(endDate + "T23:59:59");
        List<Bill> bills = reportBillsDbOutputAdapter.findEstablishmentIncomes(nit, startDateParsed, endDateParsed);

        double total = 0.00;
        for (Bill bill : bills) {
            total += bill.getTotal();
        }
        return new FindEstablishmentIncomesResponse(total, bills);
    }
}
