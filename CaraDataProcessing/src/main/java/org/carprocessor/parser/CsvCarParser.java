package org.carprocessor.parser;

import org.carprocessor.model.Car;
import java.io.*;
import java.util.*;

public class CsvCarParser implements CarParser {
    public List<Car> parse(String filePath) throws Exception {
        List<Car> cars = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine(); // skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            Car car = new Car();
            car.setBrand(parts[0]);
            car.setModel(parts[1]);
            /*car.year = Integer.parseInt(parts[2]);
            car.month = Integer.parseInt(parts[3]);
            car.day = Integer.parseInt(parts[4]);
            car.priceUSD = Double.parseDouble(parts[5]);*/
            cars.add(car);
        }
        return cars;
    }
}