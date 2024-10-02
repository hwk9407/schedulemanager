package com.sparta.schedulemanager.repository;

import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    // DI
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 생성
    public void create(Schedule schedule, Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String authorSql = "INSERT INTO author (name) VALUES (?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(authorSql,
                            Statement.RETURN_GENERATED_KEYS); // PK를 Keyholder에 담음
                            // new String[] {"authorId"}); // authorId 컬럼

                    preparedStatement.setString(1, schedule.getPassword());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 author PK
        Long authorId = keyHolder.getKey().longValue();
        author.setAuthorId(authorId);
        schedule.setAuthorId(authorId);

        // DB 새로 생성
        String sql = "INSERT INTO schedule (password, content, createDate, modifiedDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);


                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedCreateDate = schedule.getCreateDate().format(formatter);
                    String formattedModifiedDate = schedule.getModifiedDate().format(formatter);

                    preparedStatement.setString(1, schedule.getPassword());
                    preparedStatement.setString(2, schedule.getContent());
                    preparedStatement.setString(3, formattedCreateDate);
                    preparedStatement.setString(4, formattedModifiedDate);
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 schedule PK
        Long scheduleId = keyHolder.getKey().longValue();
        schedule.setScheduleId(scheduleId);

    }
}
