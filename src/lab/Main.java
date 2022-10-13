package lab;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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


        System.out.println();
        LocalDate lt = LocalDate.now();
        System.out.println("Самый дорогой гараж");
        (ElectricityList.stream()
                 .reduce((a,b)-> (a.getPrice()* (a.getCountNow()-a.getCountBefore()))>(b.getPrice()*(b.getCountNow()-b.getCountBefore())) ? a:b)).ifPresent(e->System.out.println("Номер: "+ e.getNumber()+" Сумма за прошлый месяц: "+e.getPrice()* (e.getCountNow()-e.getCountBefore())));

        System.out.println();
        System.out.println("Определение количества платежей в каждом гаражном кооперативе для самых первых владельцев гаражей (т.е. те кто владеют гаражом дольше 10 лет).");
         GarageList.parallelStream()
                 .filter(e->e.StartDate.isBefore(lt.minusYears(10)))
                 .forEach(e->System.out.println(e.getOwnerFullName()+" "+ e.getStartDate()
                 +" Кол-во платежей: "+(Period.between(e.StartDate,lt).getMonths()+Period.between(e.StartDate,lt).getYears()*12)));
        System.out.println();

        System.out.println("Определение минимального предыдущего показания счетчика для каждого гаражного кооператива");
         ElectricityList.stream()
                .sorted(Comparator.comparing(Electricity::getCountBefore))
                .filter(distinctByKey(Electricity::getAddress))
                .forEach(e->System.out.println(e.getCountBefore()+" "+e.getAddress()));
        System.out.println();

        System.out.println("Определение средней стоимости за единицу для каждого владельца. " +
                "Выводить ФИО владельца и среднюю стоимость за единицу в порядке убывания стоимости");
         Map<String, List<Electricity>> EMap = ElectricityList.parallelStream()
                 .sorted(Comparator.comparing(Electricity::getPrice))
                 .collect(Collectors.groupingBy(Electricity::getOwnerFullName));
         Map<String,Double> PMap = new HashMap<>();
        EMap.forEach((k,v)->PMap.put(k,v.stream().mapToDouble(e->e.Price).average().getAsDouble()) );
        List<Map.Entry<String, Double>> plist = new ArrayList<>(PMap.entrySet());
        plist.sort(Map.Entry.comparingByValue());
        Collections.reverse(plist);
        plist.forEach(System.out::println);

        }

    public static List<Garage> CreateGaragesCollection(String path) throws IOException {
        List<Garage> GarageList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(path));
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
        List<Electricity> ElectricityList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(path));
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