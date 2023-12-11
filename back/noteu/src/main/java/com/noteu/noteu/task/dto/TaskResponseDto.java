package com.noteu.noteu.task.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.noteu.noteu.task.entity.Task}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDto implements Serializable {
    Long id;
    String taskTitle;
    String taskContent;
    LocalDateTime deadLine;
}