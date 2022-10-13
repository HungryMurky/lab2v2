package lab;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    private final static  String SAMPLE_CSV_FILE_PATH = "./SourceData.csv";
    public static void main(String[] args) throws IOException {

        List<Garage> GarageList = CreateGaragesCollection(SAMPLE_CSV_FILE_PATH);//created garages collection
        List<Electricity> ElectricityList = CreateElectricityCollection(SAMPLE_CSV_FILE_PATH);
        System.out.println("Коллекция объектов класса Гараж" + GarageList);
        System.out.println("Коллекция объектов класса Электроэнергия"+ ElectricityList);
        System.out.println();
        System.out.println("Список владельцев с расходом более чем 200 в алфавитном порядке:");
         ElectricityList.stream()
                 .filter(e-> e.CountNow-e.CountBefore>200)
                 .sorted(Comparator.comparing(Electricity::getOwnerFullName))
                 .forEach(e->System.out.println("Владелец:"+e.OwnerFullName+
                         " Предыдущее показание счетчика:" +e.CountBefore+
                         " Текущеее показание счетчика:"+e.CountNow +
                         " Расход:" +(e.CountNow-e.CountBefore)));

        System.out.println();
        System.out.println("Вывод списка адресов без повторений:");
         ElectricityList.parallelStream()
                 .filter(distinctByKey(Electricity::getAddress))
                 .forEach(e->System.out.println(e.getAddress()));

        System.out.println();
        Scanner inp = new Scanner(System.in);
        System.out.println("Введите адрес кооператива");
        String address = inp.nextLine();
        System.out.println("Вывод списка владельцев гаражей заданного гаражного кооператива ("+address+") с указанием даты вступления в кооператив.");
         GarageList.stream()
                 .filter(e->Objects.equals(e.getAddress(), address))
                 .forEach(e->System.out.println(e.getOwnerFullName()+" "+ e.getStartDate()));

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
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    }