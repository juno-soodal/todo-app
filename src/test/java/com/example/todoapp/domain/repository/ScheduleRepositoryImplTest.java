package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ScheduleRepositoryImplTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("crud")
    @Transactional
    public void crud() {
        //등록
        Schedule savedSchedule = scheduleRepository.save(new Schedule(1L, "할일 2"));
        assertThat(savedSchedule.getToDo()).isEqualTo("할일 2");

        //단건 조회
        Schedule schedule = scheduleRepository.find(savedSchedule.getAuthorId(), savedSchedule.getId()).get();
        assertThat(schedule.getToDo()).isEqualTo("할일 2");

        //수정
        String updateToDo = "할일 수정";
        savedSchedule.updateSchedule(updateToDo);
        int updatedRow = scheduleRepository.updateSchedule(savedSchedule);
        assertThat(updatedRow).isEqualTo(1);


        //삭제
        int deletedRow = scheduleRepository.deleteSchedule(savedSchedule.getAuthorId(), savedSchedule.getId());
        assertThat(deletedRow).isEqualTo(1);


    }
}