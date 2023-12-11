package com.noteu.noteu.question.repository;

import com.noteu.noteu.question.entity.QuestionComment;
import com.noteu.noteu.question.entity.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

    List<QuestionComment> findByQuestionPost(QuestionPost questionPost);
}
