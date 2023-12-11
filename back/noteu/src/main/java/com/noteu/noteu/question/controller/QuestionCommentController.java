package com.noteu.noteu.question.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.question.dto.request.RequestQuestionCommentDTO;
import com.noteu.noteu.question.service.QuestionCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/questions/{question-id}")
public class QuestionCommentController {

    private final QuestionCommentService questionCommentService;

    @PostMapping("/question-comment")
    public String addQuestionComment(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                     @PathVariable("question-id") Long questionPostId, RequestQuestionCommentDTO requestQuestionCommentDTO) {
        questionCommentService.save(requestQuestionCommentDTO, questionPostId, memberInfo.getId());

        return "redirect:/subjects/{subject-id}/questions/{question-id}";
    }

    @GetMapping("/delete/{question-commentId}")
    public String deleteQuestionCommentById(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId,
                                            @PathVariable("question-id") Long questionPostId, @PathVariable("question-commentId") Long questionCommentId) {
        questionCommentService.deleteById(questionCommentId);

        return "redirect:/subjects/{subject-id}/questions/{question-id}";
    }
}
