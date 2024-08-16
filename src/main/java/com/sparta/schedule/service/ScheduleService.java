package com.sparta.schedule.service;

import com.sparta.schedule.ScheduleRequestDto;
import com.sparta.schedule.ScheduleResponseDto;
import com.sparta.schedule.entity.ScheduleEntity;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(ScheduleRequestDto schedule) {
        // dto -> entity 변환
        // 키워드: 빌더패턴
        ScheduleEntity entity = ScheduleEntity.builder()
                .name(schedule.getName())
                .password(schedule.getPassword())
                .work(schedule.getWork())
                .create_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        Long saveId = repository.save(entity);
        entity.setId(saveId);
        // ResponseDto 생성해서 반환을 해주고
        return new ScheduleResponseDto(entity.getId(), entity.getName(),entity.getPassword(), entity.getCreate_at(), entity.getUpdated_at(),entity.getWork());
    }

    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        ScheduleEntity entity = repository.findById(id);

        return new ScheduleResponseDto(entity.getId(), entity.getName(),entity.getPassword(), entity.getCreate_at(), entity.getUpdated_at(),entity.getWork());
    }
    public List<ScheduleResponseDto> getSchedules(String name, String updated_at) {

        List<ScheduleEntity> entities = repository.getSchedules(name, updated_at);
        List<ScheduleResponseDto> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            ScheduleResponseDto dto = new ScheduleResponseDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getPassword(),
                    entity.getCreate_at(),
                    entity.getUpdated_at(),
                    entity.getWork()
            );
            dtos.add(dto);
        }
        return dtos;
//        List<ScheduleEntity> entities = repository.getSchedules(name, updated_at);
//        return entities.stream()
//                .map(schedule -> new ScheduleResponseDto(
//                        schedule.getId(),
//                        schedule.getManagerName(),
//                        schedule.getPassword(),
//                        schedule.getCreate_at(),
//                        schedule.getUpdated_at(),
//                        schedule.getWork()))
//                .toList();
    }


    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto schedule){
        ScheduleEntity entity = repository.findById(id);

        if(entity.getId().equals(id) && entity.getPassword().equals(schedule.getPassword()))
        {
            return scheduleRepository.updateSchedule(id,schedule);
        } else return null;
    }

    public void deleteSchedule(Long id, String password){
        ScheduleEntity entity = repository.findById(id);
        if(entity.getId().equals(id) && entity.getPassword().equals(password))
        {
             scheduleRepository.deleteSchedule(id,password);
        }
    }
}
