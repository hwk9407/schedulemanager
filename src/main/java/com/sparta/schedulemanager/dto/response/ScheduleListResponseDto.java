package com.sparta.schedulemanager.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleListResponseDto extends ResponseDto {
    private final List<ScheduleDto> data;

    public ScheduleListResponseDto(int statusCode, String message, List<ScheduleDto> scheduleDtos) {
        super(statusCode, message);
        this.data = scheduleDtos;
    }
}
