package com.noteu.noteu.question.service;

import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;


public interface QuestionCommentService {

    void save(RequestQuestionCommentDTO requestQuestionCommentDTO, Long questionPostId, Long memberId);

    void deleteById(Long questionCommentId);
}
