package entity;

import entity.enums.CarLevel;

import java.io.Serializable;

public class Car implements Serializable, Comparable<Car> {

    private int carId;
    private Manufacturer manufacturer;
    private Model model;
    private String carLevel;
    private int carLvlId;
    private String bodyType;
    private int bodyTypeId;
    private String transmissionType;
    private int transmissionTypeId;
    private String fuelType;
    private int fuelTypeId;
    private float engine;
    private float fuelConsumption;
    private int productionYear;
    private Branch branch;
    private float price;
    private String imageName;

    public Car() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getCarLevel() {
        return carLevel;
    }

    public void setCarLevel(String carLevel) {
        this.carLevel = carLevel;
    }

    public int getCarLvlId() {
        return carLvlId;
    }

    public void setCarLvlId(int carLvlId) {
        this.carLvlId = carLvlId;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public float getEngine() {
        return engine;
    }

    public void setEngine(float engine) {
        this.engine = engine;
    }

    public float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(int bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public int getTransmissionTypeId() {
        return transmissionTypeId;
    }

    public void setTransmissionTypeId(int transmissionTypeId) {
        this.transmissionTypeId = transmissionTypeId;
    }

    public int getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(int fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    @Override
    public int compareTo(Car o) {
        Integer curCarLevel = -1, compareCarLevel = -1;

        if(CarLevel.ECONOM.toString().equals(this.carLevel)) {
            curCarLevel = CarLevel.ECONOM.getLevel();
        }else if(CarLevel.STANDARD.toString().equals(this.carLevel)) {
            curCarLevel = CarLevel.STANDARD.getLevel();
        }else if(CarLevel.LUX.toString().equals(this.carLevel)) {
            curCarLevel = CarLevel.LUX.getLevel();
        }

        if(CarLevel.ECONOM.toString().equals(o.carLevel)) {
            compareCarLevel = CarLevel.ECONOM.getLevel();
        }else if(CarLevel.STANDARD.toString().equals(o.carLevel)) {
            compareCarLevel = CarLevel.STANDARD.getLevel();
        }else if(CarLevel.LUX.toString().equals(o.carLevel)) {
            compareCarLevel = CarLevel.LUX.getLevel();
        }

        return curCarLevel.compareTo(compareCarLevel);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carlId=" + carId +
                ", manufacturer=" + manufacturer +
                ", model=" + model +
                ", carLevel='" + carLevel + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", engine=" + engine +
                ", fuelConsumption=" + fuelConsumption +
                ", productionYear=" + productionYear +
                ", branch=" + branch +
                ", price=" + price +
                '}';
    }

}
