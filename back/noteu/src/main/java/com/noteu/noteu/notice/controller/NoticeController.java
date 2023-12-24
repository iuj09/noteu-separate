package com.noteu.noteu.notice.controller;

import com.noteu.noteu.member.dto.MemberInfo;
import com.noteu.noteu.notice.dto.NoticeRequestDto;
import com.noteu.noteu.notice.dto.NoticeResponseDto;
import com.noteu.noteu.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects/{subject-id}/notices")
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 리스트
    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> list(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id")Long subjectId){
        ArrayList<NoticeResponseDto> notice_list = noticeService.getAll(subjectId);

        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        else if(notice_list.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(notice_list);
    }

    // 공지사항 추가
    @PostMapping
    public ResponseEntity<String> addNotice(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @RequestBody NoticeRequestDto noticeRequestDto){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            noticeService.save(noticeRequestDto, memberInfo.getId(), subjectId);
            return ResponseEntity.status(HttpStatus.CREATED).body("공지 사항 작성이 완료되었습니다.");
        }
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> getNoticeByID(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId){
        NoticeResponseDto noticeResponseDto = noticeService.getNotice(noticeId);
        if(memberInfo == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        else
            return ResponseEntity.ok(noticeResponseDto);
    }

    // 공지사항 수정
    @PutMapping("/{notice-id}")
    public ResponseEntity<String> editNotice(@AuthenticationPrincipal MemberInfo memberInfo, @RequestBody NoticeRequestDto noticeRequestDto, @PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            noticeService.updateById(noticeRequestDto, noticeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("공지 사항 수정이 완료되었습니다.");
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{notice-id}")
    public ResponseEntity<String> deleteNotice(@AuthenticationPrincipal MemberInfo memberInfo, @PathVariable("subject-id") Long subjectId, @PathVariable("notice-id") Long noticeId){
        if(memberInfo==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해 주세요.");
        else if(!memberInfo.getAuthorities().toString().contains("ROLE_TEACHER"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("선생님만 사용할 수 있는 서비스 입니다.");
        else {
            noticeService.delNotice(noticeId);
            return ResponseEntity.status(HttpStatus.OK).body("공지 사항 삭제가 완료되었습니다.");
        }
    }
}
