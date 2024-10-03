package com.sparta.schedulemanager.repository;

import com.sparta.schedulemanager.dto.response.ResponseDto;
import com.sparta.schedulemanager.dto.response.ScheduleDto;
import com.sparta.schedulemanager.entity.Author;
import com.sparta.schedulemanager.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        String authorSql = "INSERT INTO author (name) VALUES (?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(authorSql,
                            Statement.RETURN_GENERATED_KEYS); // PK를 Keyholder에 담음
                    // new String[] {"authorId"}); // authorId 컬럼

                    preparedStatement.setString(1, author.getName());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 author PK
        Long authorId = keyHolder.getKey().longValue();
        author.setAuthorId(authorId);

        return authorId;
    }

    // 일정 생성
    public Long scheduleSave(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        // DB 새로 생성
        String sql = "INSERT INTO schedule (password, content, createDate, modifiedDate, authorId) VALUES (?, ?, ?, ?, ?)";
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
}
