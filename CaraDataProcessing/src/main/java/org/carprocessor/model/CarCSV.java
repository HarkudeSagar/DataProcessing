package org.carprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarCSV {
       // @CsvBindByName(column = "Brand")
       @JsonProperty("Brand")
        public String brand;

        //@CsvBindByName(column = "Model")
        @JsonProperty("Model")
        public String model;

     //   @CsvBindByName(column = "ReleaseDate")
     @JsonProperty("ReleaseDate")
        public String releaseDateStr;

        @Override
        public String toString() {
                return "CarCSV{" +
                        "brand='" + brand + '\'' +
                        ", model='" + model + '\'' +
                        ", releaseDateStr='" + releaseDateStr + '\'' +
                        '}';
        }
        public CarCSV() {
        }
        public CarCSV(String brand, String model, String releaseDateStr) {
                this.brand = brand;
                this.model = model;
                this.releaseDateStr = releaseDateStr;
        }


}