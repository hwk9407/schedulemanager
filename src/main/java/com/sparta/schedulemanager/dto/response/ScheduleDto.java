package com.sparta.schedulemanager.dto.response;

import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDto {
    private Long scheduleId;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long authorId;
    private String name;

    public ScheduleDto(Schedule schedule, Author author) {

        // 데이터 검증
        if (schedule == null || author == null) {
            throw new IllegalArgumentException("일정이나 작성자가 하나라도 null 일 수 없습니다.");
        } else if (!author.getAuthorId().equals(schedule.getAuthorId())) {
            throw new IllegalArgumentException("작성자 아이디가 매치되지 않습니다.");
        }

        this.scheduleId = schedule.getScheduleId();
        this.content = schedule.getContent();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();
        this.authorId = schedule.getAuthorId();
        this.name = author.getName();
    }
}
