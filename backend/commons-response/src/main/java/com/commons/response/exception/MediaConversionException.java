package com.commons.response.exception;

public class MediaConversionException extends RuntimeException {
    public MediaConversionException() {
        super("Media conversion failed");
    }
}
