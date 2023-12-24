package com.noteu.noteu.question.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;
import com.noteu.noteu.question.service.QuestionCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/questions/{question-id}")
public class QuestionCommentController {

    private final QuestionCommentService questionCommentService;

    @PostMapping
    public ResponseEntity<Void> addQuestionComment(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("question-id") Long questionPostId,
                                                   @RequestBody RequestQuestionCommentDTO requestQuestionCommentDTO) {
        if(memberInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            questionCommentService.save(requestQuestionCommentDTO, questionPostId, memberInfo.getId());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @DeleteMapping("/{question-commentId}")
    public ResponseEntity<Void> deleteQuestionCommentById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("question-commentId") Long questionCommentId) {
        if(memberInfo.getId() != questionCommentService.getById(questionCommentId).getMemberId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        } else {
            questionCommentService.deleteById(questionCommentId);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
    }
}

