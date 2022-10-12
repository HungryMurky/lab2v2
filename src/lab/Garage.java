package lab;

import java.time.LocalDate;

public class Garage {
    int Number;
    String Address;
    String OwnerFullName;
    LocalDate StartDate;
    int CountBefore;
    int CountNow;
    int Price;
    LocalDate PayDate;

    public Garage(int number, String address, String ownerFullName, LocalDate startDate, int countBefore, int countNow, int price, LocalDate payDate) {
        Number = number;
        Address = address;
        OwnerFullName = ownerFullName;
        StartDate = startDate;
        CountBefore = countBefore;
        CountNow = countNow;
        Price = price;
        PayDate = payDate;
    }
}

