package com.kwala.app.enums;

/**
 * @author jacobamuchow@gmail.com
 */

public enum SyncStatus {
    VALID(0),
    DELETED(1);

    private int id;

    SyncStatus(int id) {
        this.id = id;
    }

    public static SyncStatus fromId(int id) {
        for (SyncStatus syncStatus : values()) {
            if (syncStatus.id == id) {
                return syncStatus;
            }
        }
        return VALID;
    }

    public int getId() {
        return id;
    }
}
