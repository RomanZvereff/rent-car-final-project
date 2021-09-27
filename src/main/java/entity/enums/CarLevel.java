package entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum CarLevel {

    ECONOM(1), STANDARD(2), LUX(3);

    private final int carLevelId;
    private static final Map<Integer, CarLevel> map;

    static {
        map = new HashMap<>();
        for(CarLevel value : CarLevel.values()) {
            map.put(value.carLevelId, value);
        }
    }

    CarLevel(int carLevelId) {
        this.carLevelId = carLevelId;
    }

    public static CarLevel findByKey(int key) {
        return map.get(key);
    }

    public int getLevel() {
        return carLevelId;
    }

}
