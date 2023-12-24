package com.noteu.noteu.question.service;

import com.noteu.noteu.question.dto.QuestionCommentDTO;
import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;
import com.noteu.noteu.question.dto.response.ResponseQuestionCommentDTO;


public interface QuestionCommentService {

    void save(RequestQuestionCommentDTO requestQuestionCommentDTO, Long questionPostId, Long memberId);

    ResponseQuestionCommentDTO getById(Long questionCommentId);
    void deleteById(Long questionCommentId);
}
