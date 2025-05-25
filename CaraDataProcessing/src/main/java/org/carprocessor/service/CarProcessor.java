package org.carprocessor.service;

import org.carprocessor.model.Car;
import java.util.*;
import java.util.stream.Collectors;

public class CarProcessor {
    public static List<Car> filterByBrandAndPrice(List<Car> cars, String brand, double maxPrice) {
        return cars.stream()
                .filter(c -> c.getBrand().equalsIgnoreCase(brand) && c.getPrice().getValue() <= maxPrice)
                .collect(Collectors.toList());
    }

    public static List<Car> filterByBrandAndDate(List<Car> cars, String brand, int y, int m, int d) {
        return cars.stream()
                .filter(c -> c.getBrand().equalsIgnoreCase(brand) &&
                        true)
                .collect(Collectors.toList());
    }

    public static List<Car> getFiltered(List<Car> merged, String filterBrand, Double filterPriceMin, Date startDate, Date endDate) {
        merged.stream().forEach(car -> System.out.println("car->"+ car.toString()));
        List<Car> filtered = merged.stream()
                .filter(c -> filterBrand == null || (c.getBrand() != null && c.getBrand().equalsIgnoreCase(filterBrand)))
                .filter(c -> filterPriceMin == null || (c.getPrice() != null && c.getPrice().getValue() >= filterPriceMin))
                .filter(c -> (startDate == null || (c.getReleaseDate() != null && !c.getReleaseDate().before(startDate))) &&
                        (endDate == null || (c.getReleaseDate() != null && !c.getReleaseDate().after(endDate))))
                .collect(Collectors.toList());
        return filtered;
    }

    public static void getSorted(String sortBy, List<Car> filtered) {
        if ("price".equalsIgnoreCase(sortBy)) {
            filtered.sort(Comparator.comparingDouble(c -> -c.getConvertedPrice()));
        } else if ("year".equalsIgnoreCase(sortBy)) {
            filtered.sort(Comparator.comparing((Car c) -> c.getReleaseDate()).reversed());
        }
    }
    public static List<Car> sortByPrice(List<Car> cars, boolean descending) {
        return cars.stream()
                .sorted(Comparator.comparingDouble(c -> descending ? -c.getPrice().getValue() : c.getPrice().getValue()))
                .collect(Collectors.toList());
    }

    public static List<Car> sortByYear(List<Car> cars, boolean descending) {
        return cars.stream()
                .sorted(Comparator.comparing(c -> descending ? c.getReleaseDate() : c.getReleaseDate()))
                .collect(Collectors.toList());
    }


    public static void printAsTable(List<Car> cars) {
        System.out.printf("%-10s %-10s %-5s %-12s%n", "Brand", "Model", "Type", "Price(USD)");
        System.out.println("------------------------------------------------");
        for (Car c : cars) {
            System.out.printf("%-10s %-10s %-5s $%-10.2f%n", c.getBrand(), c.getModel(), c.getType(), c.getPrice().getValue());
        }
    }

    public static void printAsJson(List<Car> cars) {
        System.out.println("[");
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            System.out.printf("  { \"brand\": \"%s\", \"model\": \"%s\", \"type\": \"%s\", \"price\": %.2f }%s%n",
                    c.getBrand(), c.getModel(), c.getType(), c.getPrice().getValue(), i < cars.size() - 1 ? "," : "");
        }
        System.out.println("]");
    }

    public static void printAsXml(List<Car> cars) {
        System.out.println("<cars>");
        for (Car c : cars) {
            System.out.printf("  <car>%n    <brand>%s</brand>%n    <model>%s</model>%n    <type>%s</type>%n    <price>%.2f</price>%n  </car>%n",
                    c.getBrand(), c.getModel(), c.getType(), c.getPrice().getValue());
        }
        System.out.println("</cars>");
    }
}