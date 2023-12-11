package com.noteu.noteu.notice.repository;

import com.noteu.noteu.notice.dto.NoticeResponseDto;
import com.noteu.noteu.notice.entity.Notice;
import com.noteu.noteu.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    ArrayList<NoticeResponseDto> findAllBySubject(Optional<Subject> subject);
}