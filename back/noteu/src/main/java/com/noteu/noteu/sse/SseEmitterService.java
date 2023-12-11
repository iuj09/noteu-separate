package com.noteu.noteu.sse;

import com.noteu.noteu.sse.dto.SseNewChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseEmitterService {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    SseEmitter add(Long memberId, SseEmitter emitter) {
        this.emitterMap.put(memberId, emitter);
        log.info("emitterMap size: {}", emitterMap.size());

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitterMap.remove(memberId);    // 만료되면 리스트에서 삭제
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }

    public void newChatRoom(SseNewChatDto sseNewChatDto){
        log.info("newChatRoom는 만들어짐");
        Optional<SseEmitter> emitter = Optional.ofNullable(emitterMap.get(sseNewChatDto.getMemberId()));
        emitter.ifPresent(e -> {
            try {
                e.send(SseEmitter.event()
                        .name("newChatRoom")
                        .data(sseNewChatDto));
            } catch (IOException ex) {
                log.info("현재 접속하지 않은 유저 입니다.");
            }
        });
    }
}
