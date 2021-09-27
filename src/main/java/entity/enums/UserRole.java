package entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {

    ADMIN(1), MANAGER(2), CUSTOMER(3);

    private final int userRoleId;
    private static final Map<Integer, UserRole> map;

    static {
        map = new HashMap<>();
        for(UserRole value : UserRole.values()) {
            map.put(value.userRoleId, value);
        }
    }

    UserRole(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public static UserRole findByKey(int key) {
        return map.get(key);
    }

    public int getUserRoleId() {
        return userRoleId;
    }

}
