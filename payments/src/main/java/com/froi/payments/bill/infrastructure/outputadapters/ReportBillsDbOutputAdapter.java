package com.froi.payments.bill.infrastructure.outputadapters;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillDetail;
import com.froi.payments.bill.domain.BillDiscount;
import com.froi.payments.bill.infrastructure.outputports.FindEstablishmentIncomesOutputPort;
import com.froi.payments.common.PersistenceAdapter;
import com.froi.payments.common.exceptions.IllegalEnumException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@PersistenceAdapter
public class ReportBillsDbOutputAdapter implements FindEstablishmentIncomesOutputPort {

    private BillDbEntityRepository billDbEntityRepository;
    private BillDetailDbEntityRepository billDetailDbEntityRepository;
    private DiscountDetailDbEntityRepository discountDetailDbEntityRepository;

    @Autowired
    public ReportBillsDbOutputAdapter(BillDbEntityRepository billDbEntityRepository, BillDetailDbEntityRepository billDetailDbEntityRepository, DiscountDetailDbEntityRepository discountDetailDbEntityRepository) {
        this.billDbEntityRepository = billDbEntityRepository;
        this.billDetailDbEntityRepository = billDetailDbEntityRepository;
        this.discountDetailDbEntityRepository = discountDetailDbEntityRepository;
    }

    @Override
    public List<Bill> findEstablishmentIncomes(String establishmentId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Bill> bills = billDbEntityRepository.findAllByEstablishmentId(establishmentId, startDate, endDate)
                .stream()
                .map(BillDbEntity::toDomainNoPaidType)
                .toList();

        for (Bill bill : bills) {
            List<BillDetail> billDetails = billDetailDbEntityRepository.findAllByBill(bill.getId().toString())
                    .stream()
                    .map(BillDetailDbEntity::toDomain)
                    .toList();
            bill.setBillDetails(billDetails);

            List<BillDiscount> billDiscounts = discountDetailDbEntityRepository.findAllByBill(bill.getId().toString())
                    .stream()
                    .map(DiscountDetailDbEntity::toDomain)
                    .toList();
            bill.setBillDiscounts(billDiscounts);
        }
        return bills;
    }
}
