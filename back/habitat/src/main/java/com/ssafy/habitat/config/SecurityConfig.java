package com.ssafy.habitat.config;

import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                /**401, 403 Exception 핸들링 */
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)

                /**세션 사용하지 않음*/
//                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
                .and()
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .anyRequest().authenticated()

                /**JwtSecurityConfig 적용 */
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .csrf().disable()
                .build();
    }
}