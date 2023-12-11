package com.noteu.noteu.subject.service;

import com.noteu.noteu.subject.dto.SubjectRequestDto;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;

import java.util.ArrayList;
import java.util.List;

public interface SubjectService {
    SubjectResponseDto save(SubjectRequestDto subjectRequestDto);
    SubjectResponseDto getSubject(Long id);
    List<Subject> getAll(Long memberId);
    Subject getSubjectByCode(String subjectCode);
    void delSubject(Long id);
}
