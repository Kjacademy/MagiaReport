package com.zolyomia.magiareport.data.rawreport.exception;

public class RawReportProcessException extends RuntimeException {

    public RawReportProcessException() {
        super();
    }

    public RawReportProcessException(String message) {
        super(message);
    }

    public RawReportProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
