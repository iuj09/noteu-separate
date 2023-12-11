package com.noteu.noteu.subject.service.impl;

import com.noteu.noteu.common.RandomCodeGenerator;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.subject.dto.SubjectRequestDto;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.entity.SubjectMember;
import com.noteu.noteu.subject.repository.SubjectMemberRepository;
import com.noteu.noteu.subject.repository.SubjectRepository;
import com.noteu.noteu.subject.service.SubjectMemberService;
import com.noteu.noteu.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMemberRepository subjectMemberRepository;

    @Override
    public SubjectResponseDto save(SubjectRequestDto subjectRequestDto){
        String subjectCode = RandomCodeGenerator.generateRandomCode();

        Subject subject = subjectRepository.save(
                Subject.builder().subjectName(subjectRequestDto.getSubjectName()).subjectCode(subjectCode).build()
        );

        return SubjectResponseDto.builder().subjectName(subject.getSubjectName()).subjectCode(subject.getSubjectCode()).build();
    }

    @Override
    public SubjectResponseDto getSubject(Long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        return SubjectResponseDto.builder().subjectName(subject.getSubjectName()).build();
    }

    @Override
    public ArrayList<Subject> getAll(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        ArrayList<Subject> list = subjectMemberRepository.findByMember(member);
        return list;
    }

    @Override
    public Subject getSubjectByCode(String subjectCode) {
        return subjectRepository.findBySubjectCode(subjectCode).orElse(null);
    }

    @Override
    public void delSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
