package com.noteu.noteu.question.repository;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.question.entity.QuestionPost;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {
    List<QuestionPost> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

    Page<QuestionPost> findBySubject(Pageable pageable, Subject subject);

    Page<QuestionPost> findBySubjectAndQuestionPostTitleContaining(Pageable pageable, Subject subject, String title);

    Page<QuestionPost> findBySubjectAndMember(Pageable pageable, Subject subjectId, Member member);

    @Transactional
    @Modifying
    @Query("update QuestionPost q set q.views = q.views + 1 where q.id = :id")
    void updateViewById(Long id);
}
