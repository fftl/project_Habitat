package com.ssafy.habitat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.exception.ErrorCode;
import com.ssafy.habitat.exception.FailResponse;
import com.ssafy.habitat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request; // 요청 데이터를 가져옵니다.
        HttpServletResponse httpServletResponse = (HttpServletResponse) response; // 응답을 담당합니다.
        String header = httpServletRequest.getHeader("AUTHORIZATION_HEADER");

        //헤더 형식 확인
        if (header == null){
            System.out.println("토큰이 존재하지 않는다는 Error 를 반환해줘야 합니다.");
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(request, response);
            return;
        }

        String jwt = resolveToken(httpServletRequest); //요청 데이터속에서 jwt를 가져옵니다.
        String requestURI = httpServletRequest.getRequestURI(); //어떤 요청이 왔는지 URI를 저장합니다.
        String chk = tokenProvider.validateToken(jwt);

        if(requestURI.equals("/users/refresh")){
            if(chk.equals("true")){
                System.out.println("재발급해야해요");
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                User user = userService.getUser(authentication.getCredentials().toString());

                if(!user.getRefreshKey().equals(jwt)){
                    setErrorResponse((HttpServletResponse) response, ErrorCode.REFRESH_TOKEN_NOT_MATCH);
                }

                //새로 생성한 refreshToken을 DB에 저장합니다.
                TokenInfo newToken = tokenProvider.createToken(authentication);
                user.setRefreshKey(newToken.getRefreshToken());
                userService.addUser(user);

                //header에 담아서
                httpServletResponse.setHeader("AccessToken", newToken.getAccessToken());
                httpServletResponse.setHeader("RefreshToken", newToken.getRefreshToken());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("완료?");

            } else if(chk.equals("expired")){
                setErrorResponse(httpServletResponse, ErrorCode.REFRESH_TOKEN_EXPIRED);
            }

        } else {
            if(StringUtils.hasText(jwt) && !chk.equals("false")){   //jwt의 유효성을 검증합니다.
                if(chk.equals("expired")){
                    System.out.println("token is expired");
                    if(requestURI.equals("/users/refresh")){
                        System.out.println("token is refresh");
                        setErrorResponse(httpServletResponse, ErrorCode.UNAUTHORIZED_USER);
                    } else {
                        System.out.println("token is expired");
                        setErrorResponse(httpServletResponse, ErrorCode.EXPIRED_JWT_EXCEPTION);
                    }
                }
                //jwt가 유효한 상태의 토큰이라면
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                //SecurityContext에 현재의 인증정보를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }else{
                LOGGER.info("유효한 JWT 토큰이 없습니다., uri: {}", requestURI);
            }
        }

        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**토큰 정보 추출 */
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        FailResponse failResponse = FailResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        try{
            response.getWriter().write(objectMapper.writeValueAsString(failResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}