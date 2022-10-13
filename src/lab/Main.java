package lab;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
    private final static  String SAMPLE_CSV_FILE_PATH = "./SourceData.csv";
    public static void main(String[] args) throws IOException {

        List<Garage> GarageList = CreateGaragesCollection(SAMPLE_CSV_FILE_PATH);//created garages collection
        List<Electricity> ElectricityList = CreateElectricityCollection(SAMPLE_CSV_FILE_PATH);
        //System.out.println(GarageList);
        //System.out.println(ElectricityList);
        System.out.println("Список владельцев с расходом более чем 200 в алфавитном порядке:");
         ElectricityList.stream()
                 .filter(e-> e.CountNow-e.CountBefore>200)
                 .sorted(Comparator.comparing(Electricity::getOwnerFullName))
                 .forEach(e->System.out.println("Владелец:"+e.OwnerFullName+
                         " Предыдущее показание счетчика:" +e.CountBefore+
                         " Текущеее показание счетчика:"+e.CountNow +
                         " Расход за месяц:" +(e.CountNow-e.CountBefore)));

        }

    public static List<Garage> CreateGaragesCollection(String path) throws IOException {
        List<Garage> GarageList = new ArrayList<Garage>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader,  CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            for (CSVRecord csvRecord : csvParser) {

                String Number = csvRecord.get("Number");
                String Address = csvRecord.get("Address");
                String OwnerFullName = csvRecord.get("OwnerFullName");
                String StartDate = csvRecord.get("StartDate");
                Garage garage = new Garage(Integer.parseInt(Number),Address,OwnerFullName,StartDate);
                GarageList.add(garage);
            }

        }
        return GarageList;
    }//returning collection of garages
    public static List<Electricity> CreateElectricityCollection(String path) throws IOException{
        List<Electricity> ElectricityList = new ArrayList<Electricity>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader,  CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            for (CSVRecord csvRecord : csvParser) {

                String Address = csvRecord.get("Address");
                String OwnerFullName = csvRecord.get("OwnerFullName");
                String Number = csvRecord.get("Number");
                String CountBefore = csvRecord.get("CountBefore");
                String CountNow = csvRecord.get("CountNow");
                String Price = csvRecord.get("Price");
                String PayDate = csvRecord.get("PayDate");
                Electricity electricity = new Electricity(Address,OwnerFullName,Integer.parseInt(Number),
                        Integer.parseInt(CountBefore),Integer.parseInt(CountNow),Integer.parseInt(Price),PayDate);

                ElectricityList.add(electricity);

            }

        }
        return ElectricityList;
    }//returning collection of electricity
    }