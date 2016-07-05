package com.raigeek.cos.service;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class WrappedResponse<T> implements Serializable {
    private HttpStatus status;
    private T response;
    private transient Exception exception;

    public WrappedResponse() {}

    public WrappedResponse(HttpStatus status) {
        this.status = status;
    }

    public WrappedResponse(HttpStatus status, T response) {
        this.status = status;
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}