package com.sparta.schedulemanager.entity;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String password;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public Schedule(ScheduleRequestDto requestDto) {
        this.scheduleId = requestDto.getScheduleId();
        this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
        this.createDate = requestDto.getCreateDate();
        this.modifiedDate = requestDto.getModifiedDate();
    }

    public Schedule(String password, String content, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.password = password;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
