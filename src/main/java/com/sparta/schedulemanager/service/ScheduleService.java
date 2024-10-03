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
    public ResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        // 일정, 글쓴이 Entity 생성
        Schedule schedule = new Schedule(scheduleRequestDto);
        Author author = new Author(scheduleRequestDto);

        // 작성일, 수정일을 현재 시간으로 설정
        schedule.setCreateDate(LocalDateTime.now());

        // 일정 DB 생성
        Long authorId = scheduleRepository.authorSave(author);
        schedule.setAuthorId(authorId);

        Long scheduleId = scheduleRepository.scheduleSave(schedule);

        // 성공적으로 생성
        return new ScheduleUpdateResponseDto(201, "등록을 성공하였습니다.", scheduleId);

        // 실패 시 ErrorResponseDto 반환
    }

    // 모든 일정 조회
    public ResponseDto getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        List<ScheduleDto> scheduleDtos = scheduleList.stream()
                .map(schedule -> new ScheduleDto(schedule, scheduleRepository.findAuthor(schedule.getAuthorId())))
                .toList();

        // 성공적으로 조회 시
        if (scheduleList != null) {
            return new ScheduleListResponseDto(200, "조회를 성공하였습니다.", scheduleDtos);
        } else {
            // 실패 시 ErrorResponseDto 반환
            return new ErrorResponseDto(400, "일정이 존재하지 않습니다.");
        }
    }

    // 특정 일정 조회
    public ResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findSchedule(scheduleId);
        ScheduleDto scheduleDto = new ScheduleDto(schedule, scheduleRepository.findAuthor(schedule.getAuthorId()));

        return new ScheduleResponseDto(200, "조회를 성공하였습니다.", scheduleDto);
    }

    // 일정 수정
    @Transactional
    public ResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        // id로 일정 Entity 생성
        Schedule schedule = scheduleRepository.findSchedule(scheduleId);


        if (schedule != null) {
            // 비밀번호 확인
            if (!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
                return new ErrorResponseDto(400, "비밀번호가 일치하지 않습니다.");
            }
            schedule.setContent(scheduleRequestDto.getContent());
            schedule.setModifiedDate(LocalDateTime.now());


            // schedule 내용 수정
            scheduleRepository.update(scheduleId, schedule);

            // 성공적으로 수정
            return new ScheduleUpdateResponseDto(201, "수정을 성공하였습니다.", scheduleId);
        } else {
            // 실패 시 ErrorResponseDto 반환
            return new ErrorResponseDto(400, "일정이 존재하지 않습니다.");
        }
    }
}
