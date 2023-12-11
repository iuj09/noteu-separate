package com.noteu.noteu.subject.repository;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.entity.SubjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface SubjectMemberRepository extends JpaRepository<SubjectMember, Long> {
    @Query(value = "select sm.subject from SubjectMember as sm where sm.member=:member")
    ArrayList<Subject> findByMember(@Param("member") Member member);

    @Query(value = "select sm.member from SubjectMember as sm where sm.subject=:subject")
    List<Member> findBySubject(@Param("subject") Subject subject);

    @Query(value = "select sm.member from SubjectMember as sm where sm.member=:member and sm.subject=:subject")
    Member findBySubjectAndMember(@Param("member") Member member, @Param("subject") Subject subject);

    @Query(value = "select sm.member from SubjectMember as sm where sm.subject.id=:subjectId")
    List<Member> findAllSubjectsBySubjectId(@Param("subjectId") Long subjectId);

    List<SubjectMember> findBySubjectId(Long subjectId);
}
