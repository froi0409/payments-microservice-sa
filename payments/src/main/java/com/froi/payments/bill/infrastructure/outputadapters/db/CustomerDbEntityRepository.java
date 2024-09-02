package com.froi.payments.bill.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDbEntityRepository extends JpaRepository<CustomerDbEntity, String> {
}
