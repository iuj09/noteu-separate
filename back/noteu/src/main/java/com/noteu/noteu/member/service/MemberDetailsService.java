package com.noteu.noteu.member.service;

import com.noteu.noteu.member.dto.*;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.entity.Role;
import com.noteu.noteu.member.exception.UserAlreadyExistAuthenticationException;
import com.noteu.noteu.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsManager{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(SignUpDto signUpDto) {

        if (userExists(signUpDto.getUsername())) {
            throw new UserAlreadyExistAuthenticationException("username exist");
        }

        Member member = Member.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword1()))
                .memberName(signUpDto.getMemberName())
                .email(signUpDto.getEmail())
                .tel(signUpDto.getTel())
                .profile("/file/profile/default.png")
                .role(Role.equals(signUpDto.getRole()))
                .build();

        memberRepository.save(member);
    }

    @Override
    public void updateUser(MemberEditDto memberEditDto) {

        Member member = getById(memberEditDto.getId());

        String editMemberName = memberEditDto.getMemberName();
        String editEmail = memberEditDto.getEmail();
        String editTel = memberEditDto.getTel();
        String editIntroduction = memberEditDto.getIntroduction();

        member.modifyInfomation(editMemberName, editEmail, editTel, editIntroduction);

        // top bar, side bar 이름을 수정하기 위해 SecurityContextHolder에 있는 Authentication 객체 다시 저장
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 권한 정보
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) ((List) authentication.getAuthorities()).stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toUnmodifiableSet());

        log.info("authorities : {} ", authorities);

        Authentication memberInfo = new UsernamePasswordAuthenticationToken(
                MemberInfo.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .memberName(editMemberName)
                        .profile(member.getProfile())
                        .authorities(authorities)
                        .build()
                , null, authorities);

        // SecurityContextHolder에 MemberInfo 저장
        SecurityContextHolder.getContext().setAuthentication(memberInfo);
        log.info("SecurityContextHolder 저장 객체: {}", SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public void deleteUser(Long memberId, String username) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (!member.getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        memberRepository.delete(member);
    }

    @Override
    public void changePassword(MemberPasswordDto memberPasswordDto) {

        Member member = getById(memberPasswordDto.getId());
        String newPassword = memberPasswordDto.getNewPassword();

        member.modifyPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        log.info("[log] 비밀번호 변경 완료!");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Member getById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return null;
        }
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFound"));

        List<SimpleGrantedAuthority> list = member.getRole().stream()
                .map(Role::getValue)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return MemberDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .profile(member.getProfile())
                .memberName(member.getMemberName())
                .authorities(list)
                .build();
    }

    public void changeProfile(Long memberId, String newProfile) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.modifyProfile(newProfile);
        memberRepository.save(member);

        // SecurityContextHolder에 있는 Authentication 객체 다시 저장
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 권한 정보
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) ((List) authentication.getAuthorities()).stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toUnmodifiableSet());

        log.info("authorities : {} ", authorities);

        Authentication memberInfo = new UsernamePasswordAuthenticationToken(
                MemberInfo.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .memberName(member.getMemberName())
                        .profile(newProfile)
                        .authorities(authorities)
                        .build()
                , null, authorities);

        // SecurityContextHolder에 MemberInfo 저장
        SecurityContextHolder.getContext().setAuthentication(memberInfo);
    }

    public Member getByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    public Member getByTel(String tel) {
        return memberRepository.findByTel(tel).orElse(null);
    }
}
