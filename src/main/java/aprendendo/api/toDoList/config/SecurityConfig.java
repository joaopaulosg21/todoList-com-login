package aprendendo.api.toDoList.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import aprendendo.api.toDoList.auth.TokenService;
import aprendendo.api.toDoList.config.filter.AuthFilter;
import aprendendo.api.toDoList.repository.UserRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.antMatchers(HttpMethod.POST,"/users").permitAll()
            .antMatchers(HttpMethod.POST,"/users/login").permitAll()
            .anyRequest().authenticated()
            .and().addFilterBefore(new AuthFilter(userRepository, tokenService), UsernamePasswordAuthenticationFilter.class);
        });
        
        http.csrf().disable();
        return http.build();
    }
}
