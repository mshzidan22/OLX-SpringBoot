package com.olx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public AccountDetailsService AccountDetailsService() {
        return new AccountDetailsService();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return   NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(AccountDetailsService());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/saveAds").authenticated()
                .and()
                .authorizeRequests().antMatchers("/unSave/**").authenticated()
                .and()
                //return only ids savedAds
                .authorizeRequests().antMatchers("/mySavedAds/**").authenticated()
                .and()
                // return real ads
                .authorizeRequests().antMatchers("/myaccount/savedAds").authenticated()
                .and()
                .authorizeRequests().antMatchers("/myaccount").authenticated()
                .and()
                .authorizeRequests().antMatchers("/canEdit/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/test/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }






}
