package com.froi.payments.customer.infrastructure.outputadapters.db;

import com.froi.payments.customer.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDbEntity {
    @Id
    @Column
    private String nit;

    @Column
    private String dpi;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Customer toDomain() {
        return Customer.builder()
                .nit(nit)
                .dpi(dpi)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .build();
    }

    public static CustomerDbEntity fromDomain(Customer customer) {
        return new CustomerDbEntity(customer.getNit(),
                customer.getDpi(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate());
    }
}
