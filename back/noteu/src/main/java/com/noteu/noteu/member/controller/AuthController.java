package com.noteu.noteu.member.controller;

import com.noteu.noteu.member.dto.SignInDto;
import com.noteu.noteu.member.dto.SignUpDto;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final MemberDetailsService memberDetailsService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDto> signup(@RequestBody SignUpDto signUpDto) {
        memberDetailsService.createUser(signUpDto);
        return new ResponseEntity<>(signUpDto, HttpStatus.CREATED);
    }

    @GetMapping("/id-check")
    public ResponseEntity<JSONObject> idCheck(String id) {
        Member member = memberDetailsService.getByUsername(id.trim());

        boolean flag = false;
        String msg;
        if (id == "") {
            msg = "ID를 입력해주세요.";
        } else if (id.length() < 4 || id.length() > 10) {
            msg = "4자 이상, 10자 이하로 작성해주세요.";
        } else if (!Pattern.matches("^[a-z0-9]+$", id)) {
            msg = "영어 소문자와 숫자로 구성해주세요.";
        } else if (member == null) {
            msg = "사용 가능한 아이디입니다.";
            flag = true;
        } else {
            msg = "다른 아이디를 입력해주세요.";
        }

        JSONObject obj = new JSONObject();
        obj.put("flag", flag);
        obj.put("msg", msg);

        return ResponseEntity.ok(obj);
    }

    @GetMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(String email) {
        Member member = memberDetailsService.getByEmail(email);
        if (member == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/tel-check")
    public ResponseEntity<Boolean> telCheck(String tel) {
        Member member = memberDetailsService.getByTel(tel);
        if (member == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
