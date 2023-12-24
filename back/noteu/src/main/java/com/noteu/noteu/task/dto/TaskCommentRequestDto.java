package com.noteu.noteu.task.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCommentRequestDto {
    String taskCommentTitle;
    String taskCommentFileName;
}