package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public class NetworkException extends Exception {

    private int code = -1;

    /**
     * Constructors without error codes
     */
    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructors with error codes
     */
    public NetworkException(int code, String message) {
        super(message);
        this.code = code;
    }

    public NetworkException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public NetworkException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
