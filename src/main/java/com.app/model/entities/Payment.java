package com.app.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    private Integer payment;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "payment")
    private Set<CustomerOrder> customerOrders = new HashSet<>();
}
