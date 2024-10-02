package com.sparta.schedulemanager.dto.response;


public class ErrorResponseDto extends ResponseDto {

    public ErrorResponseDto(int statusCode, String message) {
        super(statusCode, message);
    }
}
