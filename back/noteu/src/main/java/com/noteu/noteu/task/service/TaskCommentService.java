package com.noteu.noteu.task.service;

import com.noteu.noteu.task.dto.TaskCommentRequestDto;
import com.noteu.noteu.task.entity.TaskComment;

import java.util.List;

public interface TaskCommentService {
    void save(TaskCommentRequestDto taskCommentRequestDto, Long taskId, Long memberId);
    List<TaskComment> getAllTaskComment(Long taskId, Long memberId);
    List<TaskComment> getAll(Long taskId);
    void updateById(TaskCommentRequestDto taskCommentRequestDto, Long taskCommentId);
    void deleteTaskComment(Long taskCommentId);
}
