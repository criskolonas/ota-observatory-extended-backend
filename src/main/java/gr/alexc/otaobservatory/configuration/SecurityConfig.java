package gr.alexc.otaobservatory.configuration;

import gr.alexc.otaobservatory.configuration.filters.JwtAuthFilter;
import gr.alexc.otaobservatory.configuration.filters.RateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthenticationFilter;
    @Autowired
    private RateLimitFilter rateLimitFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/*").permitAll()  // public endpoints
                .requestMatchers("/api/admin/*").hasRole("ADMIN")
                .requestMatchers("/api/user/*").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

}
