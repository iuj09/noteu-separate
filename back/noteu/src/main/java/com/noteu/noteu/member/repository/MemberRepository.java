package com.noteu.noteu.member.repository;

import com.noteu.noteu.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);

    List<Member> findByMemberNameContaining(String memberName);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByTel(String tel);
}
