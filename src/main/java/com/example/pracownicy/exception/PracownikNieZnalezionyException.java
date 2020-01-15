package com.example.pracownicy.exception;

public class PracownikNieZnalezionyException extends RuntimeException {

    public PracownikNieZnalezionyException() {
        super();
    }

    public PracownikNieZnalezionyException(String message) {
        super(message);
    }

    public PracownikNieZnalezionyException(Throwable cause) {
        super(cause);
    }

    public PracownikNieZnalezionyException(String message, Throwable cause) {
        super(message, cause);
    }

}
