package com.sparta.schedule.repository;

import com.sparta.schedule.entity.ScheduleEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ScheduleRowMapper implements RowMapper<ScheduleEntity> {
    @Override
    public ScheduleEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       return ScheduleEntity.builder()
                .id(rs.getLong("id"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .work(rs.getString("work"))
                .create_at(rs.getTimestamp("create_at").toLocalDateTime())
                .updated_at(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
