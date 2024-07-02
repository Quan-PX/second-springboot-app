package com.quanpham.secondApp.Configuration;


import com.quanpham.secondApp.Service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
@RequiredArgsConstructor
public class SecurityHandleConfig {

//    private final UserService userService;
    private final PreFilter preFiler;

    private String[] WHITE_LIST = {"/login", "/refresh"};

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("**")
                        .allowedOrigins("http://locallhost:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP method
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(WHITE_LIST) // login dc request
                        .permitAll().anyRequest().authenticated())                                             //  tru login, tat ca can authenticate
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))         // STATELESS 0 cho phep luu token session,con statefull cho phep luu token o session
        .authenticationProvider(provider()).addFilterBefore(preFiler, UsernamePasswordAuthenticationFilter.class);      // cung cap provider. trc khi den api can filter truowcs. Filter nay con dinh nghia va sau do moi toi api
        return http.build();
    };

    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
//        provider.setUserDetailsService();   // find username trong datebase theo UserDetails cua spring
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {   // quan ly cac role, cac User
        return config.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){       // cho phep thiet lap url tu giao dien tu browser
        return webSeurity -> {
            webSeurity.ignoring()
                    .requestMatchers("/actuator/**", "/v3/**");       // allow locall FE request toi
        };
    }
}
