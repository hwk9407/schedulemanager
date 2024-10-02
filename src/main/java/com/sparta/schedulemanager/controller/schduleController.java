package com.sparta.schedulemanager.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sparta.schedulemanager.dto.request.ScheduleRequestDto;
import com.sparta.schedulemanager.dto.response.ErrorResponseDto;
import com.sparta.schedulemanager.dto.response.ResponseDto;
import com.sparta.schedulemanager.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class schduleController {
    ScheduleService scheduleService;

    public schduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleRequestDto));

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

}
