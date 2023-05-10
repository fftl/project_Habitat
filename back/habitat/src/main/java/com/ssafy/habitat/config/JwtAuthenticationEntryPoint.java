//package com.ssafy.habitat.config;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.habitat.exception.ErrorCode;
//import com.ssafy.habitat.exception.FailResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { //인증에 실패한 상황의 경우의 Exception 처리를 맡아서 하는 클래스입니다.
//
//    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//        System.out.println("JwtAuthenticationEntryPoint // authException.toString() >> " + authException.toString());
//
//        ErrorCode errorCode = ErrorCode.ALREADY_FRIEND;
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.setStatus(errorCode.getStatus());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        FailResponse failResponse = FailResponse.builder()
//                .status(errorCode.getStatus())
//                .code(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        try{
//            response.getWriter().write(objectMapper.writeValueAsString(failResponse));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//}