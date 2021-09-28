package entity;

import java.io.Serializable;

public class Model implements Serializable {

    private int modelId;
    private String modelName;
    private int numberOfPassengers;
    private Manufacturer manufacturer;

    public Model() {
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Model: modelId = " + modelId
                    + ", modelName = " + modelName
                    + ", numberOfPassengers = " + numberOfPassengers;
    }

}
