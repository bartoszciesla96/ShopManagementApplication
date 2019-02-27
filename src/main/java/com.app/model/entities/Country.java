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
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    private Set<Producer> producers = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    private Set<Shop> shops = new HashSet<>();
}
