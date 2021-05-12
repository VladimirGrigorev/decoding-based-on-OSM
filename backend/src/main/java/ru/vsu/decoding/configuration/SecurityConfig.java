package ru.vsu.decoding.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vsu.decoding.security.JwtFilter;

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
                .antMatchers(GET,"/api/v1/store-branches/findoptimal").hasRole("USER")
                .antMatchers(GET, "/api/v1/store-branches").permitAll()
                .antMatchers(GET, "/api/v1/store-branches/*").permitAll()
                .antMatchers(POST,"/api/v1/store-branches").hasRole("ADMIN")
                .antMatchers(DELETE,"/api/v1/store-branches/*").hasRole("ADMIN")
                .antMatchers(PUT,"/api/v1/store-branches/*").hasRole("ADMIN")
                .antMatchers(GET, "/api/v1/spare-parts").permitAll()
                .antMatchers(GET, "/api/v1/spare-parts/positive/*").permitAll()
                .antMatchers(GET,"/api/v1/spare-parts/all").hasRole("ADMIN")
                .antMatchers(POST,"/api/v1/spare-parts/*").hasRole("ADMIN")
                .antMatchers(DELETE,"/api/v1/spare-parts/*").hasRole("ADMIN")
                .antMatchers(PUT,"/api/v1/spare-parts/*/*").hasRole("ADMIN")
                .antMatchers(POST,"/api/v1/orders/buy").hasRole("USER")
                .antMatchers(POST,"/api/v1/me").hasAnyRole("USER", "ADMIN")
                .antMatchers(POST,"/api/v1/security/register/admin").hasRole("ADMIN")
                .antMatchers("/api/v1/security/*").anonymous()

                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
