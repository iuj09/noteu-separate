package com.noteu.noteu.question.repository;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.question.entity.QuestionPost;
import com.noteu.noteu.subject.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {
    List<QuestionPost> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

    Page<QuestionPost> findBySubject(Pageable pageable, Subject subject);
    Page<QuestionPost> findBySubjectAndQuestionPostTitleContaining(Pageable pageable, Subject subject, String title);

    Page<QuestionPost> findBySubjectAndMember(Pageable pageable, Subject subjectId, Member member);
}
