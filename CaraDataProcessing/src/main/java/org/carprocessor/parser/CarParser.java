package org.carprocessor.parser;

import org.carprocessor.model.Car;
import java.util.List;

public interface CarParser {
    List<Car> parse(String filePath) throws Exception;
}