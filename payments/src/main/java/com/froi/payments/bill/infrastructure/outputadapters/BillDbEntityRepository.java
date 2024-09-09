package com.froi.payments.bill.infrastructure.outputadapters;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Query("SELECT e FROM BillDbEntity e WHERE e.customer = :customerNit AND e.billDate BETWEEN :startDate AND :endDate AND (:establishmentId IS NULL OR :establishmentId = '' OR e.establishmentId = :establishmentId)")
    List<BillDbEntity> findAllCustomerHotelBillsBetweenDates(@Param("customerNit") String customerNit, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("establishmentId") String establishmentId);

    @Query("SELECT SUM(b.total) FROM BillDbEntity b WHERE b.customer = :customerNit AND b.billDate BETWEEN :start AND :end AND (:establishmentId IS NULL OR :establishmentId = '' OR b.establishmentId = :establishmentId) GROUP BY b.customer")
    Optional<Double> findTotalSpentByCustomer(@Param("customerNit") String customerNit, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("establishmentId") String establishmentId);

    @Query("SELECT SUM(b.total) FROM BillDbEntity b WHERE b.paidType = 0")
    Optional<Double> findHotelEarnings();

    @Query("SELECT SUM(b.total) FROM BillDbEntity b WHERE b.paidType = 1")
    Optional<Double> findRestaurantEarnings();


}
