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
    private Long authorId;

    public Schedule(ScheduleRequestDto requestDto) {
        this.scheduleId = requestDto.getScheduleId();
        this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
        this.authorId = requestDto.getAuthorId();
    }

    public Schedule(Long scheduleId, String password, String content, LocalDateTime createDate, LocalDateTime modifiedDate, Long authorId) {
        this.scheduleId = scheduleId;
        this.password = password;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.authorId = authorId;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        this.modifiedDate = this.createDate; // 수정일도 함께 설정
    }
}
