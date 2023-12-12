package com.noteu.noteu.member.dto;

import com.noteu.noteu.question.dto.RecentQuestionDto;
import com.noteu.noteu.subject.dto.SubjectInfoDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    private MemberDto memberDto;
    private List<SubjectInfoDto> subjectInfoList;
    private List<RecentQuestionDto> recentQuestionList;
}
