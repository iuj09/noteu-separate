package com.noteu.noteu.task.repository;

import com.noteu.noteu.task.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    @Query(value = "select * from task_comment where task_id=:taskId and member_id=:memberId", nativeQuery = true)
    List<TaskComment> findByTaskAndMember(@Param("taskId") Long taskId, @Param("memberId") Long memberId);

    @Query(value = "select * from task_comment where task_id=:taskId", nativeQuery = true)
    List<TaskComment> findByTask(@Param("taskId") Long taskId);
}