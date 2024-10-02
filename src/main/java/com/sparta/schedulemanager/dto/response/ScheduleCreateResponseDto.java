package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

@Getter
public class ScheduleCreateResponseDto extends ResponseDto {
    private final Long scheduleId;

    public ScheduleCreateResponseDto(int statusCode, String message, long scheduleId) {
        super(statusCode, message);
        this.scheduleId = scheduleId;
    }
}
