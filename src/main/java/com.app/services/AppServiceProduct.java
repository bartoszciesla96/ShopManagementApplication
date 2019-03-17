package com.app.services;

import com.app.exceptions.MyException;
import com.app.model.dto.CustomerOrderDto;
import com.app.model.dto.ProductDto;
import com.app.model.dto.StockDto;
import com.app.model.dto.mappers.ModelMapper;
import com.app.model.entities.*;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class AppServiceProduct {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    private final UserDataService userDataService;
    private final StockRepository stockRepository;
    private final CustomerOrderRepository customerOrderRepository;


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
                throw new MyException("STOCK IS NULL");
            }

            if (stockDto.getProductDto() == null ) {
                throw new MyException("PRODUCT IS NULL");
            }

            if (stockDto.getShopDto() == null ) {
                throw new MyException("SHOP IS NULL");
            }

            if (stockDto.getShopDto().getCountryDto() == null) {
                throw new MyException("COUNTRY IS NULL");
            }

            String shopName = stockDto.getShopDto().getName();
            String countryName = stockDto.getShopDto().getCountryDto().getName();
            Shop shop = new Shop();
            List<Shop> shops = shopRepository.findAllByName(shopName);
            if(shopName == null) {
                throw new MyException("SHOP NAME IS NOT CORRECT");
            }

            // !!!!!!!!!!!
            // rozpatrzyc dwie opcje
            // albo ktos nie poda Country i wtedy wybierasz wszystkie sklepy o danej nazwie
            // albo ktos poda Country wtedy sklep o danej nazwie ewentualnie moze byc jeden
            if(countryName == null) {
                for (int i = 0; i < shops.size(); i++) {
                    System.out.println((i + 1) + ". " + shops.get(i).getName() + " - " + shops.get(i).getCountry().getName());
                }

                int option = userDataService.getInt("Choose your shop:");
                if (option < 0 || option >= shops.size()) {
                    throw new MyException("INVALID SHOP NUMBER: " + option);
                }
                shop = shops.get(option - 1);

            }
            else
            {
                for (Shop s: shops)
                {
                    if(s.getName().equals(stockDto.getShopDto().getName()) && s.getCountry().getName().equals(stockDto.getShopDto().getName()))
                        shop = s;
                }
            }
            Stock stock = modelMapper.fromStockDtoToStock(stockDto);
            stock.setShop(shop);
            stockRepository.addOrUpdate(stock);



        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD SHOP WITH COUNTRY SERVICE");
        }
    }

    public void addCustomerOrder(CustomerOrderDto customerOrderDto) {
        try {

            if (customerOrderDto == null ) {
                throw new MyException("STOCK IS NULL");
            }

            if (customerOrderDto.getPaymentDto() == null ) {
                throw new MyException("PAYMENT IS NULL");
            }

            if (customerOrderDto.getCustomerDto() == null ) {
                throw new MyException("CUSTOMER IS NULL");
            }

            if (customerOrderDto.getProductDto() == null ) {
                throw new MyException("PRODUCT IS NULL");
            }

            if (customerOrderDto.getCustomerDto().getCountryDto() == null) {
                throw new MyException("CUSTOMER COUNTERY IS NULL");
            }

            String productName = customerOrderDto.getProductDto().getName();
            Product product = productRepository.findByName(productName).orElseThrow(() -> new MyException("GIVEN PRODUCT DOESNT EXIST"));
            Long productId = product.getId();
            Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> new MyException("NO PRODUCT FOUND"));
            int quantity = stock.getQuantity();
            if(quantity < customerOrderDto.getQuantity()) {
                throw new MyException("NOT ENOUGH QUANTITY IN STOCK");
            }
            CustomerOrder customerOrder = modelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);
            customerOrderRepository.addOrUpdate(customerOrder);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD CUSTOMER ORDER SERVICE");
        }
    }
}
