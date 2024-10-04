package com.sparta.schedulemanager.repository;

import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    // DI
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 작성자 생성
    public Long authorSave(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String authorSql = "INSERT INTO author (name, email) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(authorSql,
                            Statement.RETURN_GENERATED_KEYS); // PK를 Keyholder에 담음
                    // new String[] {"authorId"}); // authorId 컬럼

                    preparedStatement.setString(1, author.getName());
                    preparedStatement.setString(2, author.getEmail());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 author PK
        Long authorId = keyHolder.getKey().longValue();
        author.setAuthorId(authorId);

        return authorId;
    }

    // 작성자 작성일정 개수 증감
    public void authorChangeCount(Long authorId, int change) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String authorSql = "UPDATE author SET scheduleCount = scheduleCount + ? WHERE authorId = ?";

        jdbcTemplate.update(authorSql, change, authorId);
    }

    // 일정 생성
    public Long scheduleSave(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        // DB 새로 생성
        String sql = "INSERT INTO schedule (password, content, createDate, modifiedDate, authorId) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getPassword());
                    preparedStatement.setString(2, schedule.getContent());
                    preparedStatement.setObject(3, schedule.getCreateDate());
                    preparedStatement.setObject(4, schedule.getModifiedDate());
                    preparedStatement.setLong(5, schedule.getAuthorId());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 schedule PK
        Long scheduleId = keyHolder.getKey().longValue();
        schedule.setScheduleId(scheduleId);

        return scheduleId;
    }

    // 전체 조회
    public List<Schedule> findAll() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // SQL 의 결과로 받아온 데이터들을 schedule entity 타입으로 변환해줄 메서드
                Long scheduleId = resultSet.getLong("scheduleId");
                String password = resultSet.getString("password");
                String content = resultSet.getString("content");
                LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("createDate"), formatter);
                LocalDateTime modifiedDate = LocalDateTime.parse(resultSet.getString("modifiedDate"), formatter);
                Long authorId = resultSet.getLong("authorId");
                return new Schedule(scheduleId, password, content, createDate, modifiedDate, authorId);
            }
        });
    }

    // authorId로 author 엔티티 조회
    public Author findAuthor(Long authorId) {
        String sql = "SELECT * FROM author WHERE authorId = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Author author = new Author();
                author.setAuthorId(resultSet.getLong("authorId"));
                author.setName(resultSet.getString("name"));
                return author;
            } else {
                return null;
            }
        }, authorId);
    }

    // scheduleId로 author 엔티티 조회
    public Schedule findSchedule(Long scheduleId) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // SQL 의 결과로 받아온 데이터들을 schedule entity 타입으로 변환해줄 메서드
                Schedule schedule = new Schedule();
                schedule.setScheduleId(resultSet.getLong("scheduleId"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setContent(resultSet.getString("content"));
                schedule.setCreateDate(LocalDateTime.parse(resultSet.getString("createDate"), formatter));
                schedule.setModifiedDate(LocalDateTime.parse(resultSet.getString("modifiedDate"), formatter));
                schedule.setAuthorId(resultSet.getLong("authorId"));
                return schedule;
            } else {
                return null;
            }
        }, scheduleId);
    }

    public void update(Long scheduleId, Schedule schedule) {

        String sql = "UPDATE schedule SET content = ?, modifiedDate = ? WHERE scheduleId = ?";

        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);

                    preparedStatement.setString(1, schedule.getContent());
                    preparedStatement.setObject(2, schedule.getModifiedDate());
                    preparedStatement.setLong(3, scheduleId);
                    return preparedStatement;
                });
    }

    public void delete(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE scheduleId = ?";
        jdbcTemplate.update(sql, scheduleId);
    }
}
