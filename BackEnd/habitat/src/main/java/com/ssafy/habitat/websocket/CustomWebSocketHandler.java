package com.ssafy.habitat.websocket;

import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomWebSocketHandler.class);


    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 연결되었을 때 호출됩니다.
        // 클라이언트로부터 userKey를 추출하여 매핑합니다.

        // path: /api/websocket/{userKey}
        String userKey = session.getUri().getPath().split("/")[3];
        sessions.put(userKey, session);

        LOGGER.info("웹소켓 연결 : "+userKey);
        session.sendMessage(new TextMessage("안녕하세요! - Habit@"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 전송 오류가 발생했을 때 호출됩니다.
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 클라이언트가 연결을 종료했을 때 호출됩니다.
        // 매핑된 세션을 제거합니다.
        String userKey = session.getUri().getPath().split("/")[2];
        sessions.remove(userKey);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // userKey를 이용하여 매핑된 세션을 반환하는 메소드입니다.
    public WebSocketSession getSession(String userKey) {
        return sessions.get(userKey);
    }

    // user의 userKey를 추출하여 해당 유저의 세션에 message 웹소켓을 전송합니다.
    public void sendMessage(User user, String message) throws IOException {
        WebSocketSession session = getSession(user.getUserKey());
        if(session != null) {
            session.sendMessage(new TextMessage(message));
        }
    }
}