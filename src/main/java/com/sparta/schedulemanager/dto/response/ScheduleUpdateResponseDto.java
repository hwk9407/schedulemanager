package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

@Getter
public class ScheduleUpdateResponseDto extends ResponseDto {
    private final Long scheduleId;

    public ScheduleUpdateResponseDto(int statusCode, String message, long scheduleId) {
        super(statusCode, message);
        this.scheduleId = scheduleId;
    }
}
