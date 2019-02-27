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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private Integer age;
    private String name;
    private String surname;
    //country
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;


    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private Set<CustomerOrder> customerOrders = new HashSet<>();


}
