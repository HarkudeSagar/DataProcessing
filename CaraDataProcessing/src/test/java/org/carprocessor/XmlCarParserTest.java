package org.carprocessor;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.carprocessor.model.Car;
import org.carprocessor.model.CarXML;
import org.carprocessor.parser.XmlCarParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XmlCarParserTest {

    @Test
    void testXmlParsingReturnsCorrectCarCount() throws Exception {
        XmlCarParser parser = new XmlCarParser();
        List<Car> cars = parser.parse("src/test/resources/sampleCars.xml");
        assertEquals(2, cars.size());
    }

    @Test
    void testCarPriceIsParsedCorrectly() throws Exception {
       // XmlCarParser parser = new XmlCarParser();
        XmlMapper xmlMapper = new XmlMapper();
        CarXML carXml = xmlMapper.readValue(new File("src/test/resources/sampleCars.xml"), CarXML.class);
        //List<Car> cars = parser.parse("src/test/resources/sampleCars.xml");
        Car rav4 = carXml.car.stream().filter(c -> c.getModel().equals("RAV4")).findFirst().orElse(null);

        assertNotNull(rav4);
        assertEquals("SUV", rav4.getType());
        assertEquals(25000.00, rav4.getPrice().getValue(), 0.01);
        assertEquals(23000.00, rav4.getPrices().get(0).getValue(), 0.01);
    }
}