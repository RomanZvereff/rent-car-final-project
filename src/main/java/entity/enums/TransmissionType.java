package entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum TransmissionType {

    MANUAL(1), AUTOMATIC(2);

    private final int transmissionTypeId;
    private static final Map<Integer, TransmissionType> map;

    static {
        map = new HashMap<>();
        for(TransmissionType value : TransmissionType.values()) {
            map.put(value.transmissionTypeId, value);
        }
    }

    TransmissionType(int transmissionTypeId) {
        this.transmissionTypeId = transmissionTypeId;
    }

    public static TransmissionType findByKey(int key) {
        return map.get(key);
    }

    public int getTransmissionTypeId() {
        return transmissionTypeId;
    }

}
