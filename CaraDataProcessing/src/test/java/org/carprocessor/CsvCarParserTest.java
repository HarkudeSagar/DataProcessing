package org.carprocessor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.carprocessor.model.Car;
import org.carprocessor.model.CarCSV;
import org.carprocessor.parser.CsvCarParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvCarParserTest {

    @Test
    void testCsvParsing() throws Exception {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        MappingIterator<CarCSV> it = mapper.readerFor(CarCSV.class).with(schema).readValues("src/test/resources/sampleCars.csv");

        List<CarCSV> carCSVs = it.readAll();

        assertEquals(2, carCSVs.size());
        assertEquals("Toyota", carCSVs.get(0).brand);
        assertEquals("RAV4", carCSVs.get(0).model);
    }
}