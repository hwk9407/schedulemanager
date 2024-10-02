package com.sparta.schedulemanager.service;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import com.sparta.schedulemanager.dto.response.ResponseDto;
import com.sparta.schedulemanager.dto.response.ScheduleCreateResponseDto;
import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import com.sparta.schedulemanager.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public ResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // 일정, 글쓴이 Entity 생성
        Schedule schedule = new Schedule(requestDto);
        Author author = new Author(requestDto);

        // 일정 DB 생성
        Long authorId = scheduleRepository.authorSave(author);
        schedule.setAuthorId(authorId);

        Long scheduleId = scheduleRepository.scheduleSave(schedule);

        // 성공적으로 생성
        return new ScheduleCreateResponseDto(200, "등록을 성공하였습니다.", scheduleId);

        // 실패 시 ErrorResponseDto 반환
    }

}
