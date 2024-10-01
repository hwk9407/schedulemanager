package com.sparta.schedulemanager.dto.request;


import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private int scheduleId;
    private String password;
    private String content;
    private String createDate;
    private String modifiedDate;
}
