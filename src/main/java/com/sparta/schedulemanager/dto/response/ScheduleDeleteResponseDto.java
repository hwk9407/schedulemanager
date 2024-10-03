package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

@Getter
public class ScheduleDeleteResponseDto extends ResponseDto {
    private final Long deletedId;

    public ScheduleDeleteResponseDto(int statusCode, String message, Long deletedId) {
        super(statusCode, message);
        this.deletedId = deletedId;
    }
}
