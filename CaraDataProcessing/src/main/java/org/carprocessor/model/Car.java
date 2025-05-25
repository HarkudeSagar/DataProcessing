package org.carprocessor.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car {
    private String type;
    private String model;
    private String brand;
   /* public int year;
    public int month;
    public int day;*/
   @JacksonXmlProperty(localName = "price")
    private Price price;
    @JacksonXmlProperty(localName = "prices")
    private List<Price> prices;

    private Date releaseDate;
   // public Map<String, Double> prices = new HashMap<>();

    public Car() {}

    public Car(String type, String model, Price price, List<Price> prices) {
        this.type = type;
        this.model = model;
        this.price = price;
        this.prices = prices;
    }

    public Car(String type, String model, String brand, Price price, Date releaseDate) {
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Price> getPrices() {
        return prices;
    }


    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public double getConvertedPrice() {
        if ("SUV".equalsIgnoreCase(type))
            return getPriceByCurrency("EUR");
        else if ("Sedan".equalsIgnoreCase(type))
            return getPriceByCurrency("JPY");
        else if ("Truck".equalsIgnoreCase(type))
            return getPriceByCurrency("USD");
        return price.getValue();
    }

    private double getPriceByCurrency(String currency) {
        return prices.stream()
                .filter(p -> p.getCurrency().equals(currency))
                .map(Price::getValue)
                .findFirst()
                .orElse(price.getValue());
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", prices=" + prices +
                ", releaseDate=" + releaseDate +
                '}';
    }
}