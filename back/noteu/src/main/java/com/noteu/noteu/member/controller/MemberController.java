package com.noteu.noteu.member.controller;

import com.noteu.noteu.member.dto.*;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.entity.Role;
import com.noteu.noteu.member.service.MemberDetailsService;
import com.noteu.noteu.question.dto.RecentQuestionDto;
import com.noteu.noteu.question.service.QuestionPostService;
import com.noteu.noteu.subject.dto.SubjectInfoDto;
import com.noteu.noteu.subject.service.SubjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private String path = "/src/main/resources/static/file/";
    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final SubjectMemberService subjectMemberService;
    private final QuestionPostService questionPostService;

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountInfo> getAccount(@PathVariable("id") Long memberId, Model model) {
        // 회원 정보 수정
        Member member = memberDetailsService.getById(memberId);
        List<Role> roleList = new ArrayList<>(member.getRole());
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .tel(member.getTel())
                .introduction(member.getIntroduction())
                .profile(member.getProfile())
                .role(roleList)
                .build();

        // 과목 목록
        List<SubjectInfoDto> subjectInfoList = subjectMemberService.getSubjectInfoList(memberId);

        // 최근 질문글 목록
        List<RecentQuestionDto> recentQuestionList = questionPostService.getRecentQuestionList(memberId);

        AccountInfo accountInfo = AccountInfo.builder()
                .memberDto(memberDto)
                .subjectInfoList(subjectInfoList)
                .recentQuestionList(recentQuestionList)
                .build();

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<Void> editInformation(@PathVariable("id") Long memberId, @AuthenticationPrincipal MemberInfo memberInfo, @RequestBody MemberEditDto memberEditDto) {
        log.info("[check] memberInfo: {}", memberInfo.toString());
        if (!memberId.equals(memberInfo.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        memberDetailsService.updateUser(memberEditDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long memberId, @AuthenticationPrincipal MemberInfo memberInfo) {
        memberDetailsService.deleteUser(memberId, memberInfo.getUsername());
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/password/{id}")
    public String passwordForm(@PathVariable("id") Long memberId, @AuthenticationPrincipal MemberInfo memberInfo, Model model) {
        if (!memberId.equals(memberInfo.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        model.addAttribute("member", memberInfo);
        return "layout/member/change_password";
    }

    @PostMapping("/password/{id}")
    public String changePassword(@PathVariable("id") Long memberId, MemberPasswordDto memberPasswordDto) {
        memberDetailsService.changePassword(memberPasswordDto);
        return "redirect:/members/account/" + memberId;
    }

    @PostMapping("/pw-check")
    public ResponseEntity<String> passwordCheck(@AuthenticationPrincipal MemberInfo memberInfo, MemberPasswordDto memberPasswordDto) {
        Member member = memberDetailsService.getById(memberInfo.getId());
        String previousPassword = memberPasswordDto.getPreviousPassword();

        if (passwordEncoder.matches(previousPassword, member.getPassword())) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.ok("0");
        }
    }

    @GetMapping("/profile/{id}")
    public String profileForm(@PathVariable("id") Long memberId, @AuthenticationPrincipal MemberInfo memberInfo, Model model) {
        if (!memberId.equals(memberInfo.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
        model.addAttribute("member", memberInfo);
        return "layout/member/change_profile";
    }

    @PostMapping("/profile/{id}")
    public String changeProfile(@PathVariable("id") Long memberId, MultipartFile profileFile) throws IOException {
        String OriginalfileName = profileFile.getOriginalFilename();
        String currentPath = Paths.get("").toAbsolutePath().toString();

        File directory = new File(currentPath + path + "profile/");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                log.info("Directory is created!");
            } else {
                log.error("Failed to create directory!");
                throw new RuntimeException("Failed to create directory for task files");
            }
        }

        String fileName = getFileName(OriginalfileName);
        String contentType = getContentType(OriginalfileName);

        String editFileName = fileName + "_" + memberId + contentType;

        String filePath = currentPath + path + "profile/" + editFileName;
        log.info("filePath: {}", filePath);
        File newFile = new File(filePath);
        profileFile.transferTo(newFile);
        log.info("newFile: {}", newFile.getAbsolutePath());

        memberDetailsService.changeProfile(memberId, "/file/profile/" + editFileName);
        return "redirect:/members/account/{id}";
    }

    public static String getFileName(String filename) {
        int lastDotPosition = filename.lastIndexOf(".");
        if (lastDotPosition == -1) {
            return filename;
        } else {
            return filename.substring(0, lastDotPosition);
        }
    }

    public static String getContentType(String filename) {
        int lastDotPosition = filename.lastIndexOf(".");
        if (lastDotPosition == -1) {
            return filename;
        } else {
            return filename.substring(lastDotPosition);
        }
    }
}
