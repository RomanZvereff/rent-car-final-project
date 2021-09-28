package entity;

import java.io.Serializable;
import java.util.List;

public class Manufacturer implements Serializable {

    private int manufacturerId;
    private String manufacturerName;
    private Model model;
    private List<Model> models;

    public Manufacturer() {
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "manufacturerId=" + manufacturerId +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", model=" + model +
                ", models=" + models +
                '}';
    }

}
