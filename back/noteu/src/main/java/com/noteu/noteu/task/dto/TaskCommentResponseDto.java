package com.noteu.noteu.task.dto;

import com.noteu.noteu.member.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.noteu.noteu.task.entity.TaskComment}
 */
@Value
@Builder
public class TaskCommentResponseDto implements Serializable {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    Long taskId;
    LocalDateTime taskCreatedAt;
    LocalDateTime taskModifiedAt;
    Long taskSubjectId;
    String taskSubjectSubjectName;
    String taskTaskTitle;
    String taskTaskContent;
    LocalDateTime taskDeadLine;
    Long memberId;
    String memberUsername;
    String memberProfile;
    String memberMemberName;
    String memberEmail;
    String memberTel;
    List<Role> memberRole;
    String taskCommentTitle;
    String taskCommentFileName;
}