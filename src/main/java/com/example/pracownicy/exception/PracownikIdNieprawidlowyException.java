package com.example.pracownicy.exception;

public class PracownikIdNieprawidlowyException extends RuntimeException {

    public PracownikIdNieprawidlowyException() {
        super();
    }

    public PracownikIdNieprawidlowyException(final String message) {
        super(message);
    }

    public PracownikIdNieprawidlowyException(final Throwable cause) {
        super(cause);
    }

    public PracownikIdNieprawidlowyException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
