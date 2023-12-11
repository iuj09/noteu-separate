package com.noteu.noteu.reference.repository;


import com.noteu.noteu.reference.entity.ReferenceRoom;
import com.noteu.noteu.subject.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReferenceRoomRepository extends JpaRepository<ReferenceRoom, Long> {

    Page<ReferenceRoom> findBySubject(Pageable pageable, Subject subject);

    Page<ReferenceRoom> findBySubjectAndReferenceRoomTitleContaining(Pageable pageable, Subject subject, String searchWord);
}
