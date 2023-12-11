package com.noteu.noteu.member.controller;

import com.noteu.noteu.member.dto.SignUpDto;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.service.MemberDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberDetailsService memberDetailsService;

    @GetMapping("/sign-up")
    public String signup(SignUpDto signUpDto) {
        return "layout/member/sign_up";
    }

    @PostMapping("/sign-up")
    public String signup(@Valid SignUpDto signUpDto, BindingResult bindingResult) {

        // 오류 메세지
        if (bindingResult.hasErrors()) {
            return "layout/member/sign_up";
        }

        // 비밀번호 확인
        if (!signUpDto.getPassword1().equals(signUpDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "layout/member/sign_up";
        }

        memberDetailsService.createUser(signUpDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "layout/member/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "layout/member/logout";
    }

    @ResponseBody
    @GetMapping("/id-check")
    public JSONObject idCheck(String id) {
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

        return obj;
    }

    @ResponseBody
    @GetMapping("/email-check")
    public boolean emailCheck(String email) {
        Member member = memberDetailsService.getByEmail(email);
        if (member == null) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @GetMapping("/tel-check")
    public boolean telCheck(String tel) {
        Member member = memberDetailsService.getByTel(tel);
        if (member == null) {
            return false;
        }
        return true;
    }
}
