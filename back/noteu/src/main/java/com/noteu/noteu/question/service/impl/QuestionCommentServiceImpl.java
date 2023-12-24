package com.noteu.noteu.question.service.impl;

import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.question.converter.QuestionPostConverter;
import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;
import com.noteu.noteu.question.dto.response.ResponseQuestionCommentDTO;
import com.noteu.noteu.question.entity.QuestionComment;
import com.noteu.noteu.question.repository.QuestionCommentRepository;
import com.noteu.noteu.question.repository.QuestionPostRepository;
import com.noteu.noteu.question.service.QuestionCommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class QuestionCommentServiceImpl implements QuestionCommentService {

    private final QuestionCommentRepository questionCommentRepository;
    private final QuestionPostRepository questionPostRepository;
    private final MemberRepository memberRepository;
    private final QuestionPostConverter questionPostConverter;

    @Override
    public void save(RequestQuestionCommentDTO requestQuestionCommentDTO, Long questionPostId, Long memberId) {
        requestQuestionCommentDTO.setQuestionPost(questionPostRepository.getReferenceById(questionPostId));
        requestQuestionCommentDTO.setMember(memberRepository.getReferenceById(memberId));

        QuestionComment questionComment = questionPostConverter.requestQuestionCommentDtoToQuestionCommentEntity(requestQuestionCommentDTO);

        questionCommentRepository.save(questionComment);
    }

    @Override
    public ResponseQuestionCommentDTO getById(Long questionCommentId) {
        QuestionComment questionComment = questionCommentRepository.findById(questionCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Entity가 존재하지 않습니다." + questionCommentId));
        ResponseQuestionCommentDTO questionCommentDTO = questionPostConverter.questionCommentEntityToResponseQuestionCommentDTO(questionComment);

        return questionCommentDTO;
    }

    @Override
    public void deleteById(Long questionCommentId) {
        questionCommentRepository.deleteById(questionCommentId);
    }
}
