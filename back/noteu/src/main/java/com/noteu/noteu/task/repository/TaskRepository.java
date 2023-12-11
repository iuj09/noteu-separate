package com.noteu.noteu.task.repository;

import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.task.dto.TaskResponseDto;
import com.noteu.noteu.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select * from task where subject_id=:subjectId", nativeQuery = true)
    List<Task> findTasksBySubject(@Param("subjectId") Long subjectId);
}