package com.froi.payments.bill.infrastructure.outputadapters;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillDbEntityRepository extends JpaRepository<BillDbEntity, String> {
    @Query("SELECT b.customer, COUNT(b.id) AS billCount " +
            "FROM BillDbEntity b " +
            "WHERE b.billDate >= :lastYear AND b.customer IS NOT NULL " +
            "GROUP BY b.customer " +
            "ORDER BY billCount DESC")
    List<Object[]> findTop5CustomersByPaymentsInLastYear(@Param("lastYear") LocalDateTime lastYear, Pageable pageable);

    @Query("SELECT e FROM BillDbEntity e WHERE e.establishmentId = :establishmentId AND e.billDate between :startDate and :endDate")
    List<BillDbEntity> findAllByEstablishmentId(@Param("establishmentId") String establishmentId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
