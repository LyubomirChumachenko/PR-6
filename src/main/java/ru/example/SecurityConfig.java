package ru.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                // Для старых версий Spring Security (5.x)
                .antMatchers("/login*", "/register*").permitAll()
                // Добавляем правила для администраторских URL
                .antMatchers("/parfume/new", "/parfume/search").hasRole("ADMIN")
                // URL доступные всем авторизованным пользователям
                .antMatchers("/parfume").authenticated()
                .anyRequest().authenticated()
                

            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Перенаправление на главную страницу после успешного входа
                .permitAll()
            )
            .csrf((csrf) -> csrf.disable())
            .userDetailsService(customUserDetailsService);

        return http.build();
    }
}