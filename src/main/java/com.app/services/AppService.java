package com.app.services;

import com.app.exceptions.MyException;
import com.app.model.dto.*;
import com.app.model.dto.mappers.ModelMapper;
import com.app.model.entities.*;
import com.app.repository.*;
import com.app.repository.impl.*;


public class AppService {

    private ModelMapper modelMapper = new ModelMapper();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private TradeRepository tradeRepository = new TradeRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();

    public void addCustomerWithCountry(CustomerDto customerDto) {

        try {

            if (customerDto == null ) {
                throw new RuntimeException("CUSTOMER IS NULL");
            }

            if (customerDto.getCountryDto() == null ) {
                throw new RuntimeException("COUNTRY IS NULL");
            }
            if (customerRepository.findByName(customerDto.getName()).isPresent()) {
                throw new IllegalArgumentException("CUSTOMER WITH GIVEN NAME ALREADY EXISTS");
            }

            // sprawdzic czy nie ma takich samych dwoch klientow o takim samym imieniu i nazwisku pochodzacych
            // z tego samego kraju

            Country country = countryRepository
                    .findByName(customerDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(customerDto.getCountryDto()));
                country = countryRepository.findByName(customerDto.getCountryDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD COUNTRY TO DB"));
            }
            Customer customer = modelMapper.fromCustomerDtoToCustomer(customerDto);
            customer.setCountry(country);
            customerRepository.addOrUpdate(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ADD CUSTOMER WITH COUNTRY SERVICE");
        }

    }

    public void addShopWithCountry(ShopDto shopDto) {

        try {

            if (shopDto == null ) {
                throw new RuntimeException("SHOP IS NULL");
            }

            if (shopDto.getCountryDto() == null ) {
                throw new RuntimeException("SHOP IS NULL");
            }
            if (shopRepository.findByName(shopDto.getName()).isPresent()) {
                throw new IllegalArgumentException("SHOP WITH GIVEN NAME ALREADY EXISTS");
            }

            Country country = countryRepository
                    .findByName(shopDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(shopDto.getCountryDto()));
                country = countryRepository.findByName(shopDto.getCountryDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD COUNTRY TO DB"));
            }

            Shop shop = modelMapper.fromShopDtoToShop(shopDto);
            shop.setCountry(country);
            shopRepository.addOrUpdate(shop);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ADD SHOP WITH COUNTRY SERVICE");
        }

    }

    public void addProducerWithCountryAndTrade(ProducerDto producerDto) {
        try {

            if (producerDto == null ) {
                throw new RuntimeException("PRODUCER IS NULL");
            }

            if (producerDto.getCountryDto() == null ) {
                throw new RuntimeException("COUNTRY IS NULL");
            }

            if (producerDto.getTradeDto() == null ) {
                throw new RuntimeException("TRADE IS NULL");
            }

            if (producerRepository.findByName(producerDto.getName()).isPresent()) {
                throw new IllegalArgumentException("PRODUCER WITH GIVEN NAME ALREADY EXISTS");
            }

            Country country = countryRepository
                    .findByName(producerDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(producerDto.getCountryDto()));
                country = countryRepository.findByName(producerDto.getCountryDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD COUNTRY TO DB"));
            }

            Trade trade = tradeRepository
                    .findByName(producerDto.getTradeDto().getName())
                    .orElse(null);

            if (trade == null) {
                tradeRepository.addOrUpdate(modelMapper.fromTradeDtoToTrade(producerDto.getTradeDto()));
                trade = tradeRepository.findByName(producerDto.getTradeDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD TRADE TO DB"));
            }

            Producer producer = modelMapper.fromProducerDtoToProducer(producerDto);
            producer.setCountry(country);
            producer.setTrade(trade);
            producerRepository.addOrUpdate(producer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ADD SHOP WITH COUNTRY SERVICE");
        }
    }
/*
d. Nowy produkt. Pobierane są w postaci napisów nazwa, cena, nazwa kategorii oraz nazwa producenta i
nazwa kraju producenta. Dodatkowo należy podać nazwy rodzajów usług gwarancyjnych zgodnie z podanym
wcześniej zbiorem wartości typu wyliczeniowego. Naruszenie zasad poprawnego dodawania produktu do tabeli
generuje wyjątek klasy RuntimeException z przygotowanym odpowiednim komunikatem błędu.
 */
    public void addProductWithCategoryAndProducer(ProductDto productDto) {
        if (productDto == null ) {
            throw new MyException("PRODUCT IS NULL");
        }

        if (productDto.getProducerDto() == null) {
            throw new MyException("PRODUCER IS NULL");
        }

        if (productDto.getCategoryDto() == null ) {
            throw new MyException("CATEGORY IS NULL");
        }

        Long producerId = productDto.getProducerDto().getId();
        String producerName = productDto.getProducerDto().getName();
        Long categoryId = productDto.getCategoryDto().getId();
        String categoryName = productDto.getCategoryDto().getName();

        if (producerId == null && producerName == null) {
            throw new MyException("PRODUCER WITHOUT ID AND NAME");
        }

        if (categoryId == null && categoryName == null) {
            throw new MyException("CATEGORY WITHOUT ID AND NAME");
        }

        // producer
        Producer producer = producerRepository.findById(producerId).orElse(null);
        if (producer == null) {
            producer = producerRepository.findByName(producerName).orElseThrow(() -> new MyException("PRODUCER WITH NAME " + producerName + " DOESN'T EXIST"));
        }

        // EWENUALNIE DOROB SOBIE ZE PYTASZ USERA CZY CHCE DODAC PRODUCERA
        /*if (producer == null) {
            // dorobic ze ma pytac usera czy chce dodac producera
            productRepository.addOrUpdate(Producer.builder()
                    .name(producerName)
                    .country(add)
                    .trade()
                    .build());
        }*/

        // category
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            category = categoryRepository.findByName(categoryName).orElseThrow(() -> new MyException("CATEGORY WITH NAME " + categoryName + " DOESN'T EXIST"));
        }

        Product product = modelMapper.fromProductDtoToProduct(productDto);
        product.setCategory(category);
        product.setProducer(producer);
        productRepository.addOrUpdate(product);
    }

    public void addStockWithProductAndShop(StockDto stockDto) {
        try {

            if (stockDto == null ) {
                throw new RuntimeException("STOCK IS NULL");
            }

            if (stockDto.getProductDto() == null ) {
                throw new RuntimeException("PRODUCT IS NULL");
            }

            if (stockDto.getShopDto() == null ) {
                throw new RuntimeException("SHOP IS NULL");
            }


            Product product = productRepository
                    .findByName(stockDto.getProductDto().getName())
                    .orElse(null);

            if (product == null) {
                productRepository.addOrUpdate(modelMapper.fromProductDtoToProduct(stockDto.getProductDto()));
                product = productRepository.findByName(stockDto.getProductDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD PRODUCT TO DB"));
            }

            Shop shop = shopRepository
                    .findByName(stockDto.getShopDto().getName())
                    .orElse(null);

            if (shop == null) {
                shopRepository.addOrUpdate(modelMapper.fromShopDtoToShop(stockDto.getShopDto()));
                shop = shopRepository.findByName(stockDto.getShopDto().getName()).orElseThrow(() -> new RuntimeException("COULD NOT ADD SHOP TO DB"));
            }

            Stock stock = modelMapper.fromStockDtoToStock(stockDto);
            stock.setProduct(product);
            stock.setShop(shop);
            stockRepository.addOrUpdate(stock);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ADD SHOP WITH COUNTRY SERVICE");
        }
    }

    public void addCustomerOrder(CustomerOrderDto customerOrderDto) {
        try {

            if (customerOrderDto == null ) {
                throw new RuntimeException("STOCK IS NULL");
            }

            if (customerOrderDto.getPaymentDto() == null ) {
                throw new RuntimeException("PAYMENT IS NULL");
            }

            if (customerOrderDto.getCustomerDto() == null ) {
                throw new RuntimeException("CUSTOMER IS NULL");
            }

            if (customerOrderDto.getProductDto() == null ) {
                throw new RuntimeException("PRODUCT IS NULL");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ADD CUSTOMER ORDER SERVICE");
        }
    }

    public void findMostExpensiveProductsFromCategory() {

    }
    public void findOrdersByCountryAndAge(String countryName, Integer age) {}

}
