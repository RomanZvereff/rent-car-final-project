package entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum FuelType {

    PETROL(1), DIESEL(2), ELECTRICAL(3), HYBRID(4);

    private final int fuelTypeId;
    private static final Map<Integer, FuelType> map;

    static {
        map = new HashMap<>();
        for(FuelType value : FuelType.values()) {
            map.put(value.fuelTypeId, value);
        }
    }

    FuelType(int fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public static FuelType findByKey(int key) {
        return map.get(key);
    }

    public int getFuelTypeId() {
        return fuelTypeId;
    }

}
