package com.ssafy.habitat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;  //유저의 권한이 닿을 수 없는 요청이 왔을 때 발생하는 exception
//    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint; //인증정보에 문제가 있을 때 발생하는 exception
    private final JwtFilter jwtFilter;
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
//                .and()

//                /**세션 사용하지 않음*/
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                /** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
                .and()
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .anyRequest().authenticated()

                /**JwtSecurityConfig 적용 */
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .build();
    }
}