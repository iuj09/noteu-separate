package com.noteu.noteu.task.service;

import com.noteu.noteu.task.dto.TaskRequestDto;
import com.noteu.noteu.task.dto.TaskResponseDto;
import com.noteu.noteu.task.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    void save(TaskRequestDto taskRequestDto, Long memberId, Long subjectId, LocalDateTime deadLine);
    TaskResponseDto getTask(Long id);
    List<Task> getAll(Long subjectId);
    void updateById(TaskRequestDto taskRequestDto, Long taskId, LocalDateTime deadLine);
    void delTask(Long taskId);
}
