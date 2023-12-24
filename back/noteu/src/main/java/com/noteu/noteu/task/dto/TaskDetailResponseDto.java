package com.noteu.noteu.task.dto;

import com.noteu.noteu.task.entity.TaskComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDetailResponseDto {
    private TaskResponseDto task;
    private List<TaskComment> taskCommentList;
}
