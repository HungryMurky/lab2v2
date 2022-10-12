package lab;

import java.time.LocalDate;

public class Garage {
    int Number;
    String Address;
    String OwnerFullName;
    LocalDate StartDate;

    public Garage(int number, String address, String ownerFullName, LocalDate startDate) {
        Number = number;
        Address = address;
        OwnerFullName = ownerFullName;
        StartDate = startDate;
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

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }
}

