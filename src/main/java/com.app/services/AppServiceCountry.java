package com.app.services;

import com.app.exceptions.MyException;
import com.app.model.dto.CustomerDto;
import com.app.model.dto.ProducerDto;
import com.app.model.dto.ShopDto;
import com.app.model.dto.mappers.ModelMapper;
import com.app.model.entities.*;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppServiceCountry {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final ShopRepository shopRepository;
    private final ProducerRepository producerRepository;
    private final TradeRepository tradeRepository;

    public void addCustomerWithCountry(CustomerDto customerDto) {

        try {

            if (customerDto == null ) {
                throw new MyException("CUSTOMER IS NULL");
            }

            if (customerDto.getCountryDto() == null ) {
                throw new MyException("COUNTRY IS NULL");
            }
            if (customerRepository.findByName(customerDto.getName()).isPresent()) {
                throw new MyException("CUSTOMER WITH GIVEN NAME ALREADY EXISTS");
            }

            // sprawdzic czy nie ma takich samych dwoch klientow o takim samym imieniu i nazwisku pochodzacych
            // z tego samego kraju

            Country country = countryRepository
                    .findByName(customerDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(customerDto.getCountryDto()));
                country = countryRepository.findByName(customerDto.getCountryDto().getName()).orElseThrow(() -> new MyException("COULD NOT ADD COUNTRY TO DB"));
            }
            Customer customer = modelMapper.fromCustomerDtoToCustomer(customerDto);
            customer.setCountry(country);
            customerRepository.addOrUpdate(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD CUSTOMER WITH COUNTRY SERVICE");
        }

    }

    public void addShopWithCountry(ShopDto shopDto) {

        try {

            if (shopDto == null ) {
                throw new MyException("SHOP IS NULL");
            }

            if (shopDto.getCountryDto() == null ) {
                throw new MyException("SHOP IS NULL");
            }
            if (shopRepository.findByName(shopDto.getName()).isPresent()) {
                throw new MyException("SHOP WITH GIVEN NAME ALREADY EXISTS");
            }

            Country country = countryRepository
                    .findByName(shopDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(shopDto.getCountryDto()));
                country = countryRepository.findByName(shopDto.getCountryDto().getName()).orElseThrow(() -> new MyException("COULD NOT ADD COUNTRY TO DB"));
            }

            Shop shop = modelMapper.fromShopDtoToShop(shopDto);
            shop.setCountry(country);
            shopRepository.addOrUpdate(shop);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD SHOP WITH COUNTRY SERVICE");
        }

    }

    public void addProducerWithCountryAndTrade(ProducerDto producerDto) {
        try {

            if (producerDto == null ) {
                throw new MyException("PRODUCER IS NULL");
            }

            if (producerDto.getCountryDto() == null ) {
                throw new MyException("COUNTRY IS NULL");
            }

            if (producerDto.getTradeDto() == null ) {
                throw new MyException("TRADE IS NULL");
            }

            if (producerRepository.findByName(producerDto.getName()).isPresent()) {
                throw new MyException("PRODUCER WITH GIVEN NAME ALREADY EXISTS");
            }

            Country country = countryRepository
                    .findByName(producerDto.getCountryDto().getName())
                    .orElse(null);

            if (country == null) {
                countryRepository.addOrUpdate(modelMapper.fromCountryDtoToCountry(producerDto.getCountryDto()));
                country = countryRepository.findByName(producerDto.getCountryDto().getName()).orElseThrow(() -> new MyException("COULD NOT ADD COUNTRY TO DB"));
            }

            Long tradeId = producerDto.getTradeDto().getId();
            String tradeName = producerDto.getTradeDto().getName();

            Trade trade = tradeRepository.findById(tradeId).orElse(null);
            if (trade == null) {
                trade = tradeRepository.findByName(tradeName).orElseThrow(() -> new MyException("TRADE WITH NAME " + tradeName + " DOESN'T EXIST"));
            }

            Producer producer = modelMapper.fromProducerDtoToProducer(producerDto);
            producer.setCountry(country);
            producer.setTrade(trade);
            producerRepository.addOrUpdate(producer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD SHOP WITH COUNTRY SERVICE");
        }
    }
}
