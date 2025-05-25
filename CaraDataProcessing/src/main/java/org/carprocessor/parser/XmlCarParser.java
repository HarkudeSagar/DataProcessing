package org.carprocessor.parser;

import org.carprocessor.model.Car;
import org.carprocessor.model.Price;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class XmlCarParser implements CarParser {
    public List<Car> parse(String filePath) throws Exception {
        List<Car> cars = new ArrayList<>();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
        NodeList nodes = doc.getElementsByTagName("car");
        List<Price> prices = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element carElem = (Element) nodes.item(i);
            Car car = new Car();
            Price price = new Price();
            prices = new ArrayList<Price>();
            car.setType(carElem.getElementsByTagName("type").item(0).getTextContent());
            car.setModel(carElem.getElementsByTagName("model").item(0).getTextContent());
            price.setValue(Double.parseDouble(carElem.getElementsByTagName("price").item(0).getTextContent()));
            price.setCurrency(carElem.getElementsByTagName("price").item(0).getAttributes().getNamedItem("currency").getNodeValue());
            car.setPrice(price);
            System.out.println("Price->"+ price.toString());
            NodeList priceNodes = carElem.getElementsByTagName("prices");
            for (int j = 0; j < priceNodes.getLength(); j++) {
                Element priceElem = (Element) priceNodes.item(j);
                price = new Price();
                if (priceElem.hasAttribute("currency")) {
                    String curr = priceElem.getAttribute("currency");
                    double val = Double.parseDouble(priceElem.getTextContent());
                    price.setCurrency(curr);
                    price.setValue(val);
                    System.out.println("Price->"+ price.toString());
                }
                prices.add(price);
            }
            car.setPrices(prices);
            cars.add(car);
        }
        return cars;
    }
}