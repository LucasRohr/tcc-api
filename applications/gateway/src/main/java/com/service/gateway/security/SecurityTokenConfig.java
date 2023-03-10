package com.service.gateway.security;

import com.service.common.domain.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(
                    HttpMethod.POST,
                    jwtConfig.getUri(),
                    "/user-service/users/register"
                ).permitAll()
            .antMatchers(
                   HttpMethod.GET,
                   "/user-service/users/bootstrap"
                ).permitAll()
            .antMatchers(
                    HttpMethod.PUT,
                    "/invite-service/invites/invite-response"
            ).permitAll()
            .antMatchers(
                    HttpMethod.PUT,
                    "/invite-service/invites/invite-code-update"
            ).permitAll()
            .antMatchers(
                    HttpMethod.GET,
                    "/invite-service/invites/invite-check"
            ).permitAll()
            .antMatchers(
                    HttpMethod.GET,
                    "/invite-service/invites/invite-by-id"
            ).permitAll()
            .anyRequest().authenticated();
    }

    @Bean
    public JwtConfig jwtConfig()
    {
        return new JwtConfig();
    }
}