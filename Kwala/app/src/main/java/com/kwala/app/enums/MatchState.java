package com.kwala.app.enums;

/**
 * @author jacobamuchow@gmail.com
 */

public enum MatchState {
    NEW("NEW"),
    REJECT_SENT("REJECT_SENT"),
    ACCEPT_SENT("ACCEPT_SENT"),
    SUCCESS("SUCCESS"),
    EXPIRED("EXPIRED");

    private String networkValue;

    MatchState(String networkValue) {
        this.networkValue = networkValue;
    }

    public static MatchState fromNetworkValue(String networkValue) {
        for (MatchState state : values()) {
            if (state.networkValue.equals(networkValue)) {
                return state;
            }
        }
        return NEW;
    }

    public String getNetworkValue() {
        return networkValue;
    }
}
