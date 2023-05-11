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
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION_HEADER);

        String requestURI = request.getRequestURI(); //어떤 요청이 왔는지 URI를 저장합니다.
        if(requestURI.equals("/users/login")){  //로그인 요청일 경우에는 아무런 검사 없이 filter를 종료합니다.
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = resolveToken(request); //요청 데이터속에서 jwt를 가져옵니다.
        String check = tokenProvider.validateToken(jwt);

        headerCheck(response, header);
        if(requestURI.equals("/users/refresh")){
            LOGGER.info("Refresh 토큰은 유효하기 때문에 새로운 토큰을 생성합니다.");
            refreshProcess(response, jwt, check);
        } else {
            LOGGER.info("일반 요청에 대해 토큰을 검증합니다.");
            accessProcess(response, jwt, check, requestURI);
        }

        //이번 Filter를 마치고 다음 Filter에 request, response를 넘깁니다.
        filterChain.doFilter(request, response);
    }

    /**토큰 정보 추출 */
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    /**Refresh Token 검증*/
    private void refreshProcess(HttpServletResponse response, String jwt, String check){
        //refresh Token을 사용할 수 있는 상태라면
        if(check.equals("true")){
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            User user = userService.getUser(authentication.getCredentials().toString());

            if(!user.getRefreshKey().equals(jwt)){
                setErrorResponse( response, ErrorCode.REFRESH_TOKEN_NOT_MATCH);
            }

            //새로 생성한 refreshToken을 DB에 저장합니다.
            TokenInfo newToken = tokenProvider.createToken(authentication);
            user.setRefreshKey(newToken.getRefreshToken());
            userService.addUser(user);

            //header에 담아서
            response.setHeader("AccessToken", newToken.getAccessToken());
            response.setHeader("RefreshToken", newToken.getRefreshToken());

            SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(newToken.getAccessToken()));

        } else if(check.equals("expired")){
            LOGGER.info("Refresh 토큰도 사용할 수 없습니다.");
            setErrorResponse(response, ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    /**Access Process 검증 */
    private void accessProcess(HttpServletResponse response, String jwt, String check, String requestURI){
        if(StringUtils.hasText(jwt) && !check.equals("false")){   //jwt의 유효성을 검증합니다.

            //만료된 토큰이라면
            if(check.equals("expired")){
                setErrorResponse(response, ErrorCode.EXPIRED_JWT_EXCEPTION);
            }

            //jwt가 유효한 상태의 토큰이라면
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            LOGGER.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);

        }else{
            LOGGER.info("유효하지 않은 토큰입니다., uri: {}", requestURI);
        }
    }

    /**header의 null 체크를 합니다.*/
    private void headerCheck(HttpServletResponse response, String header){
        if(header == null) setErrorResponse(response, ErrorCode.HEADER_IS_NULL);
    }

    /**필터 내에서의 Exception 처리에 사용합니다.*/
    private void setErrorResponse( HttpServletResponse response, ErrorCode errorCode){
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