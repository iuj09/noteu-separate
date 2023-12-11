package com.noteu.noteu.reference.repository;

import com.noteu.noteu.reference.entity.Reference;
import com.noteu.noteu.reference.entity.ReferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    List<Reference> findByReferenceRoom(ReferenceRoom referenceRoom);

}
