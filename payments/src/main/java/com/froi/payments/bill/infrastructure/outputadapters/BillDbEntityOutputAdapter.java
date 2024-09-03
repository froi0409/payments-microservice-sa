package com.froi.payments.bill.infrastructure.outputadapters;

import com.froi.payments.bill.domain.Bill;
import com.froi.payments.bill.domain.BillDetail;
import com.froi.payments.bill.domain.BillDiscount;
import com.froi.payments.bill.infrastructure.outputports.MakeBillOutputPort;
import com.froi.payments.common.PersistenceAdapter;
import com.froi.payments.common.exceptions.IllegalEnumException;
import com.froi.payments.customer.domain.Customer;
import com.froi.payments.customer.infrastructure.outputports.FindCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@PersistenceAdapter
@Transactional
public class BillDbEntityOutputAdapter implements MakeBillOutputPort {

    private BillDbEntityRepository billDbEntityRepository;
    private BillDetailDbEntityRepository billDetailDbEntityRepository;
    private DiscountDetailDbEntityRepository discountDetailDbEntityRepository;

    @Autowired
    public BillDbEntityOutputAdapter(BillDbEntityRepository billDbEntityRepository, BillDetailDbEntityRepository billDetailDbEntityRepository, DiscountDetailDbEntityRepository discountDetailDbEntityRepository) {
        this.billDbEntityRepository = billDbEntityRepository;
        this.billDetailDbEntityRepository = billDetailDbEntityRepository;
        this.discountDetailDbEntityRepository = discountDetailDbEntityRepository;
    }


    @Override

    public Bill makeBill(Bill bill) throws IllegalEnumException {
        BillDbEntity dbEntity = BillDbEntity.fromDomain(bill);
        billDbEntityRepository.save(dbEntity);

        bill.setId(UUID.fromString(dbEntity.getId()));
        saveBillDetails(bill);
        saveDiscountDetails(bill);

        return dbEntity.toDomain();
    }

    public void saveBillDetails(Bill bill) {
        for (BillDetail billDetail : bill.getBillDetails()) {
            BillDetailDbEntity billDetailDbEntity = BillDetailDbEntity.fromDomain(billDetail, bill.getId().toString());
            billDetailDbEntityRepository.save(billDetailDbEntity);
        }
    }

    public void saveDiscountDetails(Bill bill) {
        for (BillDiscount discountDetail : bill.getBillDiscounts()) {
            DiscountDetailDbEntity discountDetailDbEntity = DiscountDetailDbEntity.fromDomain(discountDetail, bill.getId().toString());
            discountDetailDbEntityRepository.save(discountDetailDbEntity);
        }
    }

}
