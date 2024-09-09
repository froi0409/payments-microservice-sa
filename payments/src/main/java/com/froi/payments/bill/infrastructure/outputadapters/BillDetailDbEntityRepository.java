package com.froi.payments.bill.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailDbEntityRepository extends JpaRepository<BillDetailDbEntity, String> {
    List<BillDetailDbEntity> findAllByBill(String billId);
}
