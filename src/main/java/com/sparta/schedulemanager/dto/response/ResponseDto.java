package com.sparta.schedulemanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public abstract class ResponseDto {
    int statusCode;
    String message;

    // protected 접근 제어자로 서브클래스에서만 접근 가능
    protected ResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
