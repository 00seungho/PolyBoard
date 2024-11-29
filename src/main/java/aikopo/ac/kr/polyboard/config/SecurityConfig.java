package aikopo.ac.kr.polyboard.config;


import aikopo.ac.kr.polyboard.security.CustomUserDetailsService;
import aikopo.ac.kr.polyboard.security.handler.UserLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/usermodify", "/user/userprofile").authenticated()
                        .requestMatchers("/user/userlogout").authenticated()//
                        .requestMatchers("/board/**", "/error/**", "/main/", "/user/**","/api/**").permitAll() // 회원가입 및 로그인 페이지 접근 허용
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/user/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/user/login") // 로그인 처리 URL
                        .failureUrl("/user/login?error=true") // 로그인 실패 시
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 기본 URL
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 로그아웃 URL 설정
                        .logoutSuccessHandler(new UserLogoutSuccessHandler()) // 로그아웃 성공 후 현재 페이지로
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 관리 정책
                )
                .httpBasic(Customizer.withDefaults()); // HTTP 기본 인증 설정

        return http.build();
    }
}
