package com.sparta.schedulemanager.entity;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Author {
    private Long authorId;
    private String name;

    public Author(ScheduleRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public Author(String name) {
        this.name = name;
    }
}
