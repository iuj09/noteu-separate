package com.noteu.noteu.notice.dto;

import com.noteu.noteu.member.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class NoticeResponseDto implements Serializable {
    Long id;
    LocalDateTime createdAt;
    String subjectSubjectName;
    String memberUsername;
    String memberMemberName;
    String noticeTitle;
    String noticeContent;
}