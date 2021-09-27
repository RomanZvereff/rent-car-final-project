package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class Order implements Serializable {

    private long orderId;
    private String orderNumber;
    private Profile customer;
    private Calendar rentStart;
    private Calendar rentEnd;
    private Car car;
    private Branch branch;
    private String needDriver;
    private float totalCost;
    private String status;

    public Order() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Profile getCustomer() {
        return customer;
    }

    public void setCustomer(Profile customer) {
        this.customer = customer;
    }

    public Calendar getRentStart() {
        return rentStart;
    }

    public void setRentStart(Calendar rentStart) {
        this.rentStart = rentStart;
    }

    public Calendar getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(Calendar rentEnd) {
        this.rentEnd = rentEnd;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getNeedDriver() {
        return needDriver;
    }

    public void setNeedDriver(String needDriver) {
        this.needDriver = needDriver;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", customer=" + customer +
                ", rentStart=" + rentStart +
                ", rentEnd=" + rentEnd +
                ", car=" + car +
                ", branch=" + branch +
                ", needDriver='" + needDriver + '\'' +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                '}';
    }

}
