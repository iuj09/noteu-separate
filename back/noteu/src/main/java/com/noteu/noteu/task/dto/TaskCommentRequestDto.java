package com.noteu.noteu.task.dto;

import com.noteu.noteu.member.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.noteu.noteu.task.entity.TaskComment}
 */
@Value
@Builder
public class TaskCommentRequestDto implements Serializable {
    String taskCommentTitle;
    String taskCommentFileName;
}