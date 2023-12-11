package com.noteu.noteu.subject.service;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.dto.SubjectInfoDto;

import java.util.ArrayList;
import java.util.List;

public interface SubjectMemberService {
    // 추가(과목 참여)
    void save(String subjectCode, Long memberId);
    ArrayList<Member> getSubjectMember(Long subjectId);
    Member getMemberBySubjectCode(Long memberId, Long subjectId);
    List<SubjectInfoDto> getSubjectInfoList(Long memberId);
}
