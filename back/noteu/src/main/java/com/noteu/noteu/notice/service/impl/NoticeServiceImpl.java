package com.noteu.noteu.notice.service.impl;

import com.noteu.noteu.member.repository.MemberRepository;
import com.noteu.noteu.notice.dto.NoticeRequestDto;
import com.noteu.noteu.notice.dto.NoticeResponseDto;
import com.noteu.noteu.notice.entity.Notice;
import com.noteu.noteu.notice.repository.NoticeRepository;
import com.noteu.noteu.notice.service.NoticeService;
import com.noteu.noteu.subject.dto.SubjectResponseDto;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.subject.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final SubjectRepository subjectRepository;
    private final MemberRepository memberRepository;

    @Override
    public NoticeResponseDto save(NoticeRequestDto noticeRequestDto, Long memberId, Long subjectId) {
        Notice notice = noticeRepository.save(
                Notice.builder()
                .subject(subjectRepository.findById(subjectId).orElse(null))
                .member(memberRepository.findById(memberId).orElse(null))
                .noticeTitle(noticeRequestDto.getNoticeTitle())
                .noticeContent(noticeRequestDto.getNoticeContent())
                .build()
        );
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .createdAt(notice.getCreatedAt())
                .subjectSubjectName(notice.getSubject().getSubjectName())
                .memberUsername(notice.getMember().getUsername())
                .memberMemberName(notice.getMember().getMemberName())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);

        if(notice == null)
            return null;

        return NoticeResponseDto.builder()
                .id(notice.getId())
                .createdAt(notice.getCreatedAt())
                .subjectSubjectName(notice.getSubject().getSubjectName())
                .memberUsername(notice.getMember().getUsername())
                .memberMemberName(notice.getMember().getMemberName())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ArrayList<NoticeResponseDto> getAll(Long subjectId) {
        Optional<Subject> subjectResponseDto = subjectRepository.findById(subjectId);
        ArrayList<NoticeResponseDto> notice_list = noticeRepository.findAllBySubject(subjectResponseDto);

        return notice_list;
    }

    @Override
    public void updateById(NoticeRequestDto noticeRequestDto, Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다 :" + id));
        notice.updateNotice(noticeRequestDto.getNoticeTitle(), noticeRequestDto.getNoticeContent());
    }

    @Override
    public void delNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}
