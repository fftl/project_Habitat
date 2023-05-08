//package com.ssafy.habitat.websocket;
//
//import com.ssafy.habitat.entity.User;
//import org.springframework.stereotype.Service;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.HashMap;
//
//@Service
//@ServerEndpoint(value="/websocket/{userKey}")
//public class WebSocketNotification {
//    private static HashMap<String, Session> clients = new HashMap<>();
//
//    @OnOpen
//    public void onOpen(Session s, @PathParam("userKey") final String userKey) throws IOException {
//        System.out.println("open session : "+s.toString()+", userKey : "+userKey);
//
//        if(!clients.containsKey(s)) {
//            clients.put(userKey, s);
//            System.out.println("session open!! "+s);
//        } else {
//            System.out.println("이미 연결된 session");
//        }
//    }
//
//    @OnClose
//    public void onClose(Session s) throws IOException {
//        clients.remove(s);
//        System.out.println("세션을 닫습니다");
//    }
//    @OnMessage
//    public void onMessage(String message, Session session) throws Exception {
//        System.out.println("입력된 메세지입니다. > " + message);
//    }
//
//    public void sendMessage(String message, User user) throws IOException {
//        //user에게 message 알림 전송
//        String userKey = user.getUserKey();
//        Session session = clients.get(userKey);
//
//        // 세션이 열려있다면 알림 전송
//        if(session != null) {
//            clients.get(userKey).getBasicRemote().sendText(message);
//        } else {
//            System.out.println("세션 열려있지 않음");
//        }
//    }
//}
