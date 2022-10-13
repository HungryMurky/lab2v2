package lab;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static  String SAMPLE_CSV_FILE_PATH = "./SourceData.csv";
    public static void main(String[] args) throws IOException {

        List<Garage> GarageList = CreateGaragesCollection(SAMPLE_CSV_FILE_PATH);//created garages collection
        System.out.println(GarageList.toString());

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
                // Accessing Values by Column Index
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
        return null;
    }
    }