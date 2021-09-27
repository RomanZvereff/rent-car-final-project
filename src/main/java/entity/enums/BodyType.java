package entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum BodyType {

    SEDAN(1), HATCHBACK(2), STATION_WAGON(3), MINI_VANS(4), PICKUP(5), SUV(6);

    public final int bodyTypeId;
    private static final Map<Integer, BodyType> map;

    static {
        map = new HashMap<>();
        for(BodyType value : BodyType.values()) {
            map.put(value.bodyTypeId, value);
        }
    }

    BodyType(int bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public static BodyType findByKey(int key) {
        return map.get(key);
    }

    public int getBodyTypeId() {
        return bodyTypeId;
    }

}
