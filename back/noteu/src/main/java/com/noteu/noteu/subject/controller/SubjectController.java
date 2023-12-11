package com.noteu.noteu.subject.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.dto.SubjectMemberRequestDto;
import com.noteu.noteu.subject.dto.SubjectRequestDto;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.entity.SubjectMember;
import com.noteu.noteu.subject.service.SubjectMemberService;
import com.noteu.noteu.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMemberService subjectMemberService;

    // 과목 추가폼
    @GetMapping("/add-form")
    public String addForm(){
        return "layout/subject/add";
    }

    // 과목 추가
    @PostMapping
    public String addSubject(@AuthenticationPrincipal MemberInfo memberInfo, SubjectRequestDto subjectRequestDto){
        SubjectResponseDto subjectResponseDto = subjectService.save(subjectRequestDto);

        subjectMemberService.save(subjectResponseDto.getSubjectCode(), memberInfo.getId());
        return "redirect:/subjects";
    }

    // 과목 코드 입력 페이지 폼
    @GetMapping("/input-code-form")
    public String inputCodeForm() {
        return "layout/subject/input_subject_code";
    }

    // 과목 코드 입력
    @PostMapping("/input-code")
    public ResponseEntity<String> inputCode(@AuthenticationPrincipal MemberInfo memberInfo, SubjectMemberRequestDto subjectMemberRequestDto){
        String subjectCode = subjectMemberRequestDto.getSubjectCode();

        Subject subject = subjectService.getSubjectByCode(subjectCode);

        Member result = subjectMemberService.getMemberBySubjectCode(memberInfo.getId(), subject.getId());
        if (result == null) {
            subjectMemberService.save(subjectCode, memberInfo.getId());
            return ResponseEntity.ok("가입이 완료되었습니다");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 과목입니다");
        }
    }

    // 과목 리스트
    @GetMapping
    public String list(@AuthenticationPrincipal MemberInfo memberInfo, Model m){
        List<Subject> list = subjectService.getAll(memberInfo.getId());

        if (list != null)
            if (!list.isEmpty())
                m.addAttribute("list", list);
        return "layout/subject/list";

    }

//    // 과목 삭제
//    @PostMapping("")
//    public void delSubject(){
//
//    }
}
