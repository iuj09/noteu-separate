package com.noteu.noteu.notice.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class NoticeRequestDto implements Serializable {
    Long subjectId;
    String subjectSubjectName;
    Long memberId;
    String memberUsername;
    String memberMemberName;
    String noticeTitle;
    String noticeContent;
}