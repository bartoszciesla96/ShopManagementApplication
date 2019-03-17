package com.app.services;

import com.app.exceptions.MyException;
import com.app.model.dto.*;
import com.app.model.dto.mappers.ModelMapper;
import com.app.model.entities.*;
import com.app.repository.*;
import com.app.repository.impl.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AppService {

    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public List<Shop> findForeignGoodsInStock() {
        List<Stock>  stocks = stockRepository
                .findAll()
                .stream()
                .filter(s -> s.getShop().getCountry().getName().equals(s.getProduct().getProducer().getCountry().getName()))
                .collect(Collectors.toList());
        return shopRepository
                .findAll()
                .stream()
                .filter(s -> s.getStocks().equals(stocks))
                .collect(Collectors.toList());
    }
    public List<Producer> findOverproduction(String tradeName, int minQuantity) {
        List<Stock> stocks = stockRepository
                .findAll()
                .stream()
                .filter(s -> s.getQuantity() > minQuantity)
                .collect(Collectors.toList());
        List<Product> products = productRepository
               .findAll()
               .stream()
               .filter(p -> p.getStocks().equals(stocks))
               .collect(Collectors.toList());
        return producerRepository
                .findAll()
                .stream()
                .filter(p -> p.getTrade().getName().equals(tradeName))
                .filter(p -> p.getProducts().equals(products))
                .collect(Collectors.toList());
    }

    public List<CustomerOrder> findOrderByDateAndValue(LocalDate aDate, LocalDate zDate, BigDecimal value) {
        return null;
    }

    public List<Product> findCustomerOrders(String customerName, String customerSurname, String customerCountry) {
        List<Customer> customers = customerRepository
                .findAll()
                .stream()
                .filter(c -> c.getName().equals(customerName) && c.getSurname().equals(customerSurname) && c.getCountry().getName().equals(customerCountry))
                .collect(Collectors.toList());
        List<CustomerOrder> customerOrders = customerOrderRepository
                .findAll()
                .stream()
                .filter(c -> c.getCustomer().equals(customers))
                .collect(Collectors.toList());
        return productRepository
                .findAll()
                .stream()
                .filter(p -> p.getCustomerOrders().equals(customerOrders))
                //.sorted(p -> p.getProducer().getName())
                .collect(Collectors.toList());
    }

    public List<Customer> customerOrderStatistics() {
        List<CustomerOrder> customerOrders = customerOrderRepository
                .findAll()
                .stream()
                .filter(c -> c.getProduct().getProducer().getCountry().getName().equals(c.getCustomer().getCountry().getName()))
                .collect(Collectors.toList());

        return customerRepository
                .findAll()
                .stream()
                .filter(c -> c.getCustomerOrders().equals(customerOrders))
                .collect(Collectors.toList());

        }
    }





