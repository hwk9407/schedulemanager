package com.sparta.schedulemanager.controller;

import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import com.sparta.schedulemanager.dto.response.ResponseDto;
import com.sparta.schedulemanager.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class schduleController {
    ScheduleService scheduleService;

    public schduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성
    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        ResponseDto responseDto = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);

        /*
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleRequestDto));
        } catch (InputMissingException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(400, "누락된 입력이 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        } catch (InvalidFormatException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(400, "잘못된 형식을 입력하였습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        }*/
    }

    // 전체 일정 조회
    @GetMapping("/schedule")
    public ResponseEntity<ResponseDto> getAllSchedules() {

        ResponseDto responseDto = scheduleService.getAllSchedules();
        return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);
    }

    // 특정 일정 조회
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseDto> getSchedule(@PathVariable Long scheduleId) {

        ResponseDto responseDto = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);
    }

    // 일정 수정
    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        ResponseDto responseDto = scheduleService.updateSchedule(scheduleId, scheduleRequestDto);
        return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);
    }

    // 일정 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseDto> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        ResponseDto responseDto = scheduleService.deleteSchedule(scheduleId, scheduleRequestDto);
        return ResponseEntity.status(responseDto.getStatusCode()).body(responseDto);
    }
}
