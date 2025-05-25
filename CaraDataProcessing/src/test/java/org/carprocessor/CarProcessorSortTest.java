package org.carprocessor;

import org.carprocessor.model.Car;
import org.carprocessor.model.Price;
import org.carprocessor.service.CarProcessor;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CarProcessorSortTest {

    @Test
    void testSortByPriceDescending() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<Car> cars = List.of(
                new Car("SUV", "A", "Brand", new Price("USD",25000.00), sdf.parse("04/25/2023")),
                new Car("SUV", "B", "Brand", new Price("USD",26000.00), sdf.parse("04/25/2022"))
        );
        List<Car> sorted = CarProcessor.sortByPrice(cars, true);
        assertEquals("B", sorted.get(0).getModel());
    }

    @Test
    void testSortByYearDescending() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<Car> cars = List.of(
                new Car("SUV", "A", "Brand", new Price("USD",25000.00), sdf.parse("04/25/2023")),
                new Car("SUV", "B", "Brand", new Price("USD",26000.00), sdf.parse("04/25/2022"))
        );
        List<Car> sorted = CarProcessor.sortByYear(cars, true);
        assertEquals("B", sorted.get(0).getModel());
    }
}
