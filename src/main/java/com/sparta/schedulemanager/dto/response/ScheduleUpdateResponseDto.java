package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

@Getter
public class ScheduleUpdateResponseDto extends ResponseDto {
    private final Long scheduleId;
    private final Long authorId;

    public ScheduleUpdateResponseDto(int statusCode, String message, Long authorId, Long scheduleId) {
        super(statusCode, message);
        this.scheduleId = scheduleId;
        this.authorId = authorId;
    }
}
