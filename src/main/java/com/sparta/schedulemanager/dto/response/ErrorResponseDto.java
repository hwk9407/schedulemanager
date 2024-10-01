package com.sparta.schedulemanager.dto.response;


public class ErrorResponseDto extends ResponseDto {

    protected ErrorResponseDto(int statusCode, String message) {
        super(statusCode, message);
    }
}
