package com.noteu.noteu.subject.service.impl;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.entity.Role;
import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.subject.dto.SubjectInfoDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.entity.SubjectMember;
import com.noteu.noteu.subject.repository.SubjectMemberRepository;
import com.noteu.noteu.subject.repository.SubjectRepository;
import com.noteu.noteu.subject.service.SubjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SubjectMemberServiceImpl implements SubjectMemberService {

    private final SubjectMemberRepository subjectMemberRepository;
    private final SubjectRepository subjectRepository;
    private final MemberRepository memberRepository;

    @Override
    public void save(String subjectCode, Long memberId) {
        Optional<Subject> optionalSubject = subjectRepository.findBySubjectCode(subjectCode);

        Subject subject = null;
        if (optionalSubject.isPresent()) {
            subject = optionalSubject.get();
        }
        Member member = memberRepository.findById(memberId).orElse(null);

        subjectMemberRepository.save(SubjectMember.builder().subject(subject).member(member).build());
    }

    @Override
    public ArrayList<Member> getSubjectMember(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        ArrayList<Member> subjectMember = (ArrayList<Member>) subjectMemberRepository.findBySubject(subject);
        return subjectMember;
    }

    @Override
    public Member getMemberBySubjectCode(Long memberId, Long subjectId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        Member result = subjectMemberRepository.findBySubjectAndMember(member, subject);
        if (result == null)
            return null;

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectInfoDto> getSubjectInfoList(Long memberId) {
        List<Long> subjectIdList = new ArrayList<>();
        Member member = memberRepository.findById(memberId).orElseThrow();
        ArrayList<Subject> subjectList = subjectMemberRepository.findByMember(member);

        // 가입한 과목의 subjectId list
        for (Subject subject : subjectList) {
            subjectIdList.add(subject.getId());
        }

        List<SubjectInfoDto> subjectInfoList = new ArrayList<>();
        for(Long subjectId: subjectIdList) {
            // 과목의 선생님
            List<SubjectMember> subjectMemberList = subjectMemberRepository.findBySubjectId(subjectId);
            SubjectMember filterTeacher = subjectMemberList.stream()
                    .filter(subjectMember -> subjectMember.getMember().getRole().contains(Role.TEACHER))
                    .findFirst()
                    .orElseThrow();

            // 참여 날짜
            Member joinedMember = getMemberBySubjectCode(memberId, subjectId);
            LocalDateTime joinedAt = joinedMember.getCreatedAt();

            SubjectInfoDto subjectInfo = SubjectInfoDto.builder()
                    .memberId(memberId)
                    .subjectId(subjectId)
                    .teacherName(filterTeacher.getMember().getMemberName())
                    .subjectName(filterTeacher.getSubject().getSubjectName())
                    .joinedAt(joinedAt)
                    .build();

            subjectInfoList.add(subjectInfo);
        }
        return subjectInfoList;
    }

}
