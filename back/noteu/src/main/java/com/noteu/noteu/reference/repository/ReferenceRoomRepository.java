package com.noteu.noteu.reference.repository;


import com.noteu.noteu.reference.dto.response.DetailResponseReferenceRoomDTO;
import com.noteu.noteu.reference.entity.ReferenceRoom;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ReferenceRoomRepository extends JpaRepository<ReferenceRoom, Long> {

    @Transactional
    @Modifying
    @Query("update ReferenceRoom r set r.views = r.views + 1 where r.id = :id")
    void updateViewById(Long id);

    Page<ReferenceRoom> findBySubject(Pageable pageable, Subject subject);

    Page<ReferenceRoom> findBySubjectAndReferenceRoomTitleContaining(Pageable pageable, Subject subject, String searchWord);
}
