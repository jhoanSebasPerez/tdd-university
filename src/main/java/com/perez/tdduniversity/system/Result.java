package com.perez.tdduniversity.system;

public record Result<T> (Boolean flag, Integer status, String message, T data) {
    public Result(Boolean flag, Integer status, String message) {
        this(flag, status, message, null);
    }
}
