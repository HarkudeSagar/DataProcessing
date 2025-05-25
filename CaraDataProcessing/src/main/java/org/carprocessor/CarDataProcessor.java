package org.carprocessor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.carprocessor.model.Car;
import org.carprocessor.model.CarCSV;
import org.carprocessor.model.CarXML;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.carprocessor.service.CarProcessor.getFiltered;
import static org.carprocessor.service.CarProcessor.getSorted;

public class CarDataProcessor {

    public static void main(String[] args) throws Exception {
        Map<String, String> argMap = parseArgs(args);

        String xmlPath = argMap.getOrDefault("--input-xml", "carsType.xml");
        String csvPath = argMap.getOrDefault("--input-csv", "CarsBrand.csv");
        String filterBrand = argMap.get("--filter-brand");
        Double filterPriceMin = argMap.containsKey("--filter-price-min") ? Double.parseDouble(argMap.get("--filter-price-min")) : null;
        String startDateStr = argMap.get("--release-date-start");
        String endDateStr = argMap.get("--release-date-end");
        String sortBy = argMap.getOrDefault("--sort-by", "price");
        String outputFormat = argMap.getOrDefault("--output-format", "json");
        boolean convertCurrency = Boolean.parseBoolean(argMap.getOrDefault("--convert-currency", "true"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = startDateStr != null ? dateFormat.parse(startDateStr) : null;
        Date endDate = endDateStr != null ? dateFormat.parse(endDateStr) : null;

        XmlMapper xmlMapper = new XmlMapper();
        CarXML carXml = xmlMapper.readValue(new File(xmlPath), CarXML.class);

        /*List<CarCSV> carCSVs = new CsvToBeanBuilder<CarCSV>(new FileReader(csvPath))
                .withType(CarCSV.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build().parse();*/

        File csvFile = new File(csvPath);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        MappingIterator<CarCSV> it = mapper.readerFor(CarCSV.class).with(schema).readValues(csvFile);

        List<CarCSV> carCSVs = it.readAll();

        Map<String, CarCSV> csvMap = new HashMap<>();
        for (CarCSV c : carCSVs) {
            System.out.println("Carscsv string->"+ c.toString());
            csvMap.put(c.model, c);
        }


        List<Car> merged = carXml.car.stream()
                .peek(c -> {
                    CarCSV csv = csvMap.get(c.getModel());

                    if (csv != null) {
                        System.out.println("csv string->"+ csv.toString());
                        c.setBrand(csv.brand);
                        try {
                            c.setReleaseDate(dateFormat.parse(csv.releaseDateStr));
                        } catch (Exception ignored) {}
                    }
                })
                .collect(Collectors.toList());

        List<Car> filtered = getFiltered(merged, filterBrand, filterPriceMin, startDate, endDate);

        getSorted(sortBy, filtered);

        switch (outputFormat.toLowerCase()) {
            case "json":
                ObjectMapper jsonMapper = new ObjectMapper();
                System.out.println(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered));
                break;
            case "xml":
                System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered));
                break;
            case "table":
                System.out.printf("%-10s %-10s %-10s %-10s\n", "Brand", "Model", "Type", "Price");
                for (Car car : filtered) {
                    System.out.printf("%-10s %-10s %-10s %-10.2f\n",
                            car.getBrand(), car.getModel(), car.getType(), convertCurrency ? car.getConvertedPrice() : car.getPrice().getValue());
                }
                break;
        }
    }




    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].startsWith("--")) {
                map.put(args[i], args[i + 1]);
                i++;
            }
        }
        return map;
    }
}
