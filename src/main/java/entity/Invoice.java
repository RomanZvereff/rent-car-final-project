package entity;

import java.io.Serializable;
import java.util.Random;

public class Invoice implements Serializable {

    private long invoiceId;
    private int invoiceNumber;
    private Car car;
    private Profile customer;
    private String descriptionOfDamage;
    private float amount;
    private String account;
    private Order order;

    public Invoice() {
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Profile getCustomer() {
        return customer;
    }

    public void setCustomer(Profile customer) {
        this.customer = customer;
    }

    public String getDescriptionOfDamage() {
        return descriptionOfDamage;
    }

    public void setDescriptionOfDamage(String descriptionOfDamage) {
        this.descriptionOfDamage = descriptionOfDamage;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int generateInvoiceNumber() {
        return 100000 + new Random().nextInt(900000);
    }



}
