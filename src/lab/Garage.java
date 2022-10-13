package lab;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Garage {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    int Number;
    String Address;
    String OwnerFullName;
    LocalDate StartDate;

    public Garage(int number, String address, String ownerFullName, String startDate) {
        Number = number;
        Address = address;
        OwnerFullName = ownerFullName;
        StartDate = LocalDate.parse(startDate,formatter);
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getOwnerFullName() {
        return OwnerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        OwnerFullName = ownerFullName;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = LocalDate.parse(startDate);
    }

    @Override
    public String toString() {
        return
                "Number=" + Number +
                ", Address='" + Address + '\'' +
                ", OwnerFullName='" + OwnerFullName + '\'' +
                ", StartDate=" + StartDate;
    }
}

