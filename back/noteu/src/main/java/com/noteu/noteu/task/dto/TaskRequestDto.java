package com.noteu.noteu.task.dto;

import com.noteu.noteu.member.entity.Role;
import com.noteu.noteu.subject.entity.Subject;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.noteu.noteu.task.entity.Task}
 */
@Value
@Builder
public class TaskRequestDto implements Serializable {
    String taskTitle;
    String taskContent;
}