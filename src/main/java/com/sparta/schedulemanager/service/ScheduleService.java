package com.sparta.schedulemanager.service;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import com.sparta.schedulemanager.dto.response.*;
import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import com.sparta.schedulemanager.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

        // 작성일, 수정일을 현재 시간으로 설정
        schedule.setCreateDate(LocalDateTime.now());

        // 일정 DB 생성
        Long authorId = scheduleRepository.authorSave(author);
        schedule.setAuthorId(authorId);

        Long scheduleId = scheduleRepository.scheduleSave(schedule);

        // 성공적으로 생성
        return new ScheduleCreateResponseDto(201, "등록을 성공하였습니다.", scheduleId);

        // 실패 시 ErrorResponseDto 반환
    }

    // 모든 일정 조회
    public ResponseDto getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        List<ScheduleDto> scheduleDtos = scheduleList.stream()
                .map(schedule -> new ScheduleDto(schedule, scheduleRepository.findAuthor(schedule.getAuthorId())))
                .toList();

        // 성공적으로 조회 시
        return new ScheduleListResponseDto(200, "조회를 성공하였습니다.", scheduleDtos);

        // 실패 시 ErrorResponseDto 반환
    }

    public ResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findSchedule(scheduleId);
        ScheduleDto scheduleDto = new ScheduleDto(schedule, scheduleRepository.findAuthor(schedule.getAuthorId()));

        return new ScheduleResponseDto(200, "조회를 성공하였습니다.", scheduleDto);
    }
}
