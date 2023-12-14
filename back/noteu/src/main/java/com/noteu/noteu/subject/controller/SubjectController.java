package com.noteu.noteu.subject.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.dto.SubjectMemberRequestDto;
import com.noteu.noteu.subject.dto.SubjectRequestDto;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.service.SubjectMemberService;
import com.noteu.noteu.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMemberService subjectMemberService;

    // 과목 추가
    @PostMapping
    public ResponseEntity<String> addSubject(@AuthenticationPrincipal MemberInfo memberInfo, @RequestBody SubjectRequestDto subjectRequestDto) {

        if(memberInfo==null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 후 이용해주세요.");
        }
        else if(subjectRequestDto==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과목 생성에 실패하였습니다.");
        }
        else {
            SubjectResponseDto subjectResponseDto = subjectService.save(subjectRequestDto);
            subjectMemberService.save(subjectResponseDto.getSubjectCode(), memberInfo.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body("과목 생성이 완료되었습니다.");
        }
    }

    // 과목 코드 입력
    @PostMapping("/input-code")
    public ResponseEntity<String> inputCode(@AuthenticationPrincipal MemberInfo memberInfo, @RequestBody SubjectMemberRequestDto subjectMemberRequestDto){
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
    public ResponseEntity<List<SubjectResponseDto>> list(@AuthenticationPrincipal MemberInfo memberInfo){
        List<SubjectResponseDto> list = subjectService.getAll(memberInfo.getId());

        if (list != null)
            return ResponseEntity.ok(list);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
