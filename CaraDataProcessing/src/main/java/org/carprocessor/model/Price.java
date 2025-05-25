package org.carprocessor.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Price {

        @JacksonXmlProperty(isAttribute = true)
        private String currency;
        @JacksonXmlText
        private double value;



    public Price(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }
    public Price() {
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency='" + currency + '\'' +
                ", value=" + value +
                '}';
    }
}