package com.sparta.schedule.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScheduleEntity {
    private Long id;
    private String name;
    private String password;
    private LocalDateTime create_at;
    private LocalDateTime updated_at;
    private String work;
}
