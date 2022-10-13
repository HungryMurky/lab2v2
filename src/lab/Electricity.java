package lab;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Electricity {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    String Address;
    String OwnerFullName;
    int Number;
    int CountBefore;
    int CountNow;
    int Price;
    LocalDate PayDate;

    public Electricity(String address, String ownerFullName, int number, int countBefore, int countNow, int price, String payDate) {
        Address = address;
        OwnerFullName = ownerFullName;
        Number = number;
        CountBefore = countBefore;
        CountNow = countNow;
        Price = price;
        PayDate = LocalDate.parse(payDate,formatter);
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

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getCountBefore() {
        return CountBefore;
    }

    public void setCountBefore(int countBefore) {
        CountBefore = countBefore;
    }

    public int getCountNow() {
        return CountNow;
    }

    public void setCountNow(int countNow) {
        CountNow = countNow;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public LocalDate getPayDate() {
        return PayDate;
    }

    public void setPayDate(LocalDate payDate) {
        PayDate = payDate;
    }

    @Override
    public String toString() {
        return
                "Адрес кооператива'" + Address + '\'' +
                ", ФИО'" + OwnerFullName + '\'' +
                ", Номер'" + Number + '\'' +
                ", Предыдущее показание счетчика'" + CountBefore +'\'' +
                ", Текущее показание счетчика'" + CountNow +'\'' +
                ", Стоимость за единицу'" + Price +'\'' +
                ", Дата уплаты'" + PayDate + '\'' ;
    }
}
