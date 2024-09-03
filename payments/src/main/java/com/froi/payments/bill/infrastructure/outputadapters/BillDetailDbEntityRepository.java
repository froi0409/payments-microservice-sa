package com.froi.payments.bill.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailDbEntityRepository extends JpaRepository<BillDetailDbEntity, String> {
}
