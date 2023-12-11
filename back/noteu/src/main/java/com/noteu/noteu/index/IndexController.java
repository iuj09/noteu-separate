package com.noteu.noteu.index;

import com.noteu.noteu.member.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String home(@AuthenticationPrincipal MemberInfo memberInfo) {
        log.info("홈으로 이동하려 한다. ");

        if (Objects.nonNull(memberInfo)) {
            return "redirect:/subjects";
        } else {
            return "index";
        }
    }


}
