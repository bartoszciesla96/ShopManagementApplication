package com.app.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    //category
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;
    //producer
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<CustomerOrder> customerOrders = new HashSet<>();
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<Stock> stocks = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "guaranteecomponents",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "guaranteecomponents")
    @Enumerated(EnumType.STRING)
    private Set<GuaranteeComponents> guaranteeComponents;
}
