package com.sparta.schedulemanager.dto.response;

import com.sparta.schedulemanager.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto extends ResponseDto {
    private Long scheduleId;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long authorId;
    private String name;

    public ScheduleResponseDto(int statusCode, String message, ScheduleDto scheduleDto) {
        super(statusCode, message);
        this.scheduleId = scheduleDto.getScheduleId();
        this.content = scheduleDto.getContent();
        this.createDate = scheduleDto.getCreateDate();
        this.modifiedDate = scheduleDto.getModifiedDate();
        this.authorId = scheduleDto.getAuthorId();
        this.name = scheduleDto.getName();
    }
}
