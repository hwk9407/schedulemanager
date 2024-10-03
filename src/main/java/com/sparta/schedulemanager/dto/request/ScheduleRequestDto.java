package com.sparta.schedulemanager.dto.request;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private Long scheduleId;
    private String name;
    private String password;
    private String content;
    private Long authorId;
}
