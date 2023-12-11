package com.noteu.noteu.notice.service;

import com.noteu.noteu.notice.dto.NoticeRequestDto;
import com.noteu.noteu.notice.dto.NoticeResponseDto;

import java.util.ArrayList;

public interface NoticeService {
    NoticeResponseDto save(NoticeRequestDto noticeRequestDto, Long memberId, Long subjectId);
    NoticeResponseDto getNotice(Long id);
    ArrayList<NoticeResponseDto> getAll(Long subjectId);
    void updateById(NoticeRequestDto noticeRequestDto, Long id);
    void delNotice(Long id);
}
