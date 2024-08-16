package com.sparta.schedule.controller;


import com.sparta.schedule.ScheduleRequestDto;
import com.sparta.schedule.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 파라미터로 controller에 들어와서 service로 넘겨주고 service에서 entity생성해주고 -> repository
    @PostMapping("/schedules")
    public ScheduleResponseDto postSchedule(@RequestBody ScheduleRequestDto schedule) {
        // controller -> service.save -> respository.save() 실제 db에 저장
        return scheduleService.save(schedule);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/schedules/list")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String updated_at)
    {
        return scheduleService.getSchedules(name, updated_at);
    }

    @PutMapping("/schedules")
    public ScheduleResponseDto updateSchedule(@RequestParam(value = "id") Long id, @RequestBody ScheduleRequestDto schedule)
    {

        return scheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/schedules")
    public void deleteSchedule(@RequestParam Long id, @RequestParam String password)
    {
         scheduleService.deleteSchedule(id,password);
    }
}
