package com.noteu.noteu.notice.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.notice.dto.NoticeRequestDto;
import com.noteu.noteu.notice.dto.NoticeResponseDto;
import com.noteu.noteu.notice.service.NoticeService;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/add-form")
    public String addForm(@PathVariable("subject-id") Long subjectId, Model m){
        m.addAttribute("subjectId", subjectId);
        return "layout/notice/add";
    }

    @GetMapping
    public String list(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id")Long subjectId, Model m){
        ArrayList<NoticeResponseDto> notice_list = noticeService.getAll(subjectId);

        m.addAttribute("notice_list", notice_list);
        m.addAttribute("subjectId", subjectId);

        return "layout/notice/list";
    }

    @PostMapping
    public String addNotice(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, NoticeRequestDto noticeRequestDto){
        noticeService.save(noticeRequestDto, memberInfo.getId(), subjectId);

        return "redirect:/subjects/{subject-id}/notices";
    }

    @GetMapping("/{notice-id}")
    public String detailForm(@PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId, Model m){
        NoticeResponseDto noticeResponseDto = noticeService.getNotice(noticeId);
        m.addAttribute("notice", noticeResponseDto);
        m.addAttribute("subjectId", subjectId);
        return "layout/notice/detail";
    }

    @PostMapping("/{notice-id}")
    public String deleteNotice(@PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId){
        noticeService.delNotice(noticeId);

        return "redirect:/subjects/{subject-id}/notices";
    }

    @GetMapping("/{notice-id}/edit-form")
    public String editForm(@PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId, Model m){
        NoticeResponseDto noticeResponseDto = noticeService.getNotice(noticeId);

        m.addAttribute("notice", noticeResponseDto);
        m.addAttribute("subjectId", subjectId);
        return "layout/notice/edit";
    }

    @PostMapping("/edit/{notice-id}")
    public String editNotice(@PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId, NoticeRequestDto noticeRequestDto){
        noticeService.updateById(noticeRequestDto, noticeId);

        return "redirect:/subjects/{subject-id}/notices";
    }
}
