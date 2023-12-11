package com.noteu.noteu.sse;

import com.noteu.noteu.member.dto.MemberInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {

    private final SseEmitterService sseEmitters;

    public SseController(SseEmitterService sseEmitters) {
        this.sseEmitters = sseEmitters;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@AuthenticationPrincipal MemberInfo memberInfo) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(memberInfo.getId(), emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data(memberInfo.getMemberName()+"sse success connect."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }
}
