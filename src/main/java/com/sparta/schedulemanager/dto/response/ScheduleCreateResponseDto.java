package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

@Getter
public class ScheduleCreateResponseDto extends ResponseDto {
    private final Long scheduleId;

    public ScheduleCreateResponseDto(String message, int statusCode, long scheduleId) {
        super(statusCode, message);
        this.scheduleId = scheduleId;
    }
}
