# DataProcessing

## Modifications/changes 
- Have added Model to given csv file

## To run the program use below command
### java -cp build/libs/CaraDataProcessing-1.0.jar org.carprocessor.CarDataProcessor --input-xml carsType.xml --input-csv CarsBrand.csv --filter-brand Toyota --filter-price-min 20000 --sort-by price --output-format table --convert-currency true

### gradle commands
./gradlew clean build

### Build a fat jar
./gradlew shadowJar


