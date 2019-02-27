package com.app;

import com.app.exceptions.MyException;
import com.app.model.dto.*;
import com.app.model.entities.Customer;
import com.app.repository.CustomerRepository;
import com.app.repository.connection.DbConnection;
import com.app.repository.impl.CustomerRepositoryImpl;
import com.app.services.AppService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            final AppService service = new AppService();

//            CustomerDto c1 = CustomerDto.builder().age(20).name("ADAM").surname("KOWAL").countryDto(CountryDto.builder().name("POLSKA").build()).build();
//            service.addCustomerWithCountry(c1);
//
//            ShopDto sh1 = ShopDto.builder().name("SKLEPIK").countryDto(CountryDto.builder().name("POLSKA").build()).build();
//            service.addShopWithCountry(sh1);

            ProductDto pd1 = ProductDto.builder().name("P2").price(new BigDecimal("300")).producerDto(ProducerDto.builder().name("PA").build()).categoryDto(CategoryDto.builder().name("C1").build()).build();
            service.addProductWithCategoryAndProducer(pd1);

        } catch (MyException e) {
            e.printStackTrace();
        }

    }
}
