package com.spring.adaimdb.secutiry;

import com.spring.adaimdb.secutiry.handler.CustomAuthenticationSuccessHandler;
import com.spring.adaimdb.secutiry.handler.CustomLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomLogoutHandler customLogoutHandler;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomLogoutHandler customLogoutHandler, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customLogoutHandler = customLogoutHandler;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        "/login",
                                        "/h2-console/**",
                                        "/api",
                                        "/swagger.html"
                                        )
                                .permitAll()
                                .anyRequest()
                                .authenticated())

                .formLogin(it -> it.defaultSuccessUrl("/home", true).successHandler(customAuthenticationSuccessHandler))
                .logout(logout->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .logoutSuccessHandler(customLogoutHandler())
                                .permitAll());
        return http.build();
    }

    private LogoutSuccessHandler customLogoutHandler() {
        return this.customLogoutHandler;
    }
}
