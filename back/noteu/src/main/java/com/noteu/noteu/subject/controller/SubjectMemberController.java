package com.noteu.noteu.subject.controller;

import com.noteu.noteu.subject.service.SubjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectMemberController {

    private final SubjectMemberService subjectMemberService;
}
