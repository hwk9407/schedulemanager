package com.sparta.schedulemanager.entity;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Author {
    private Long authorId;
    private String name;
    private String email;
    private int scheduleCount;
    private LocalDateTime registrationDate;
    private LocalDateTime updateDate;

    public Author(ScheduleRequestDto requestDto) {
        this.authorId = requestDto.getAuthorId();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }

}
