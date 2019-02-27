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
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //country
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "shop")
    private Set<Stock> stocks = new HashSet<>();
}

