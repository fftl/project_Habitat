package com.ssafy.habitat.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    @Autowired
    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request; // 요청 데이터를 가져옵니다.
        String header = httpServletRequest.getHeader("AUTHORIZATION_HEADER");
        System.out.println(header);
        //헤더 형식 확인
        if (header == null){
            System.out.println("header null 아니야?");
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(request, response);
            return;
        }

        String jwt = resolveToken(httpServletRequest); //요청 데이터속에서 jwt를 가져옵니다.

        System.out.println("jwt 토큰이 존재하나요? >> " + jwt);
        String requestURI = httpServletRequest.getRequestURI(); //어떤 요청이 왔는지 URI를 저장합니다.

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){   //jwt의 유효성을 검증합니다.

            //jwt가 유효한 상태의 토큰이라면
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            //SecurityContext에 현재의 인증정보를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);

        }else{
            LOGGER.info("유효한 JWT 토큰이 없습니다., uri: {}", requestURI);
        }

        chain.doFilter(httpServletRequest, response);
    }

    /**토큰 정보 추출 */
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }
}