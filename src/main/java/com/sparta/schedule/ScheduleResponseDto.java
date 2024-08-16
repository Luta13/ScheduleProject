package com.sparta.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String password;
    private LocalDateTime create_at;
    private LocalDateTime updated_at;
    private String work;
}
