package com.app.services;

import com.app.exceptions.MyException;
import com.app.model.entities.*;
import com.app.repository.CustomerOrderRepository;
import com.app.repository.CustomerRepository;
import com.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AppServiceProductFind {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public Map<Category, Product> findMostExpensiveProductsFromCategory() {
        return productRepository
                .findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(Product::getCategory,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(
                                                Comparator.comparing(Product::getPrice)),
                                        productOptional -> productOptional.orElseThrow(() -> new MyException("PRODUCT IS NOT CORRECT")))));
    }

    public List<Product> findProductsByCustomerCountryAndAge(String countryName, int maxAge, int minAge) {
        List<Customer> customers = customerRepository
                .findAll()
                .stream()
                .filter(c -> c.getAge() < maxAge && c.getAge() > minAge)
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
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }

    public List<Product> findByVarranty(GuaranteeComponents guaranteeComponents) {
        return productRepository
                .findAll()
                .stream()
                .filter(p -> p.getGuaranteeComponents().equals(guaranteeComponents))
                //.collect(Collectors.groupingBy(Product::getCategory))
                .collect(Collectors.toList());
    }
}
