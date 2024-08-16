package com.sparta.schedule.repository;

import com.sparta.schedule.ScheduleRequestDto;
import com.sparta.schedule.ScheduleResponseDto;
import com.sparta.schedule.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleRowMapper rowMapper;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public Long save(ScheduleEntity schedule) {
        // jdbc
        String sql = "INSERT INTO manager (name, password, create_at, updated_at, work) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, schedule.getName());
            preparedStatement.setString(2, schedule.getPassword());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(schedule.getCreate_at()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(schedule.getUpdated_at()));
            preparedStatement.setString(5, schedule.getWork());
            return preparedStatement;
        }, keyHolder);

        // db에 생성한 필드 id값 가져오기
        // 키워드: jdbc 키홀더
        return keyHolder.getKey().longValue();
    }

    public ScheduleEntity findById(Long id) {
        String sql = "SELECT * FROM manager WHERE id = ?";
        // jdbcTemplate.query(); // 여러 개 조회
        // 키워드: jdbc query for object, rowmapper
        ScheduleEntity schedule = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return schedule;
    }

    public List<ScheduleEntity> getSchedules(String name, String updated_At) {
        StringBuilder sql = new StringBuilder("SELECT * FROM manager");
        List<Object> params = new ArrayList<>();
        boolean condition = false;
        if(name != null && !name.isEmpty()){
            sql.append(" WHERE name = ?");
            params.add(name);
            condition = true;
        }
        if(updated_At != null && !updated_At.isEmpty())
        {
            if(condition)
                sql.append(" AND");
            else
                sql.append(" WHERE");

            sql.append(" updated_at = ?");
            params.add(updated_At);
            condition = true;
        }
        sql.append(" ORDER BY updated_at DESC");
        return jdbcTemplate.query(sql.toString(), new ScheduleRowMapper(), params.toArray());
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto schedule) {


        String sql = "UPDATE manager SET name = ?, work = ?, updated_at = ? WHERE id = ?";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        int row = jdbcTemplate.update(sql, schedule.getName(), schedule.getWork(),now,id);

        String selectSql = "SELECT * FROM manager WHERE id = ?";

        ScheduleEntity scheduleEntity = jdbcTemplate.queryForObject(selectSql, rowMapper, id);
        return ScheduleResponseDto.builder()
                .id(scheduleEntity.getId())
                .name(scheduleEntity.getName())
                .password(scheduleEntity.getPassword())
                .work(scheduleEntity.getWork())
                .create_at(scheduleEntity.getCreate_at())
                .updated_at(scheduleEntity.getUpdated_at())
                .build();
    }

    public void deleteSchedule(Long id, String password)
    {
        String sql = "DELETE FROM manager WHERE id = ? AND password = ?";
        jdbcTemplate.update(sql, id, password);
    }
}
