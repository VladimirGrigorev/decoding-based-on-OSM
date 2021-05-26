package ru.vsu.gecoding.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vsu.gecoding.security.JwtFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(GET,"/api/v1/user/profile").hasAnyRole("USER", "ADMIN")
                .antMatchers(POST,"/api/v1/geocoding/find").permitAll()
                .antMatchers(POST,"/api/v1/question").hasRole("USER")
                .antMatchers("/api/v1/security/*").anonymous()

                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
