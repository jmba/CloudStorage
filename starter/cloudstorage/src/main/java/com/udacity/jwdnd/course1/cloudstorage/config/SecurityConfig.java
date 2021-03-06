package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.authentication.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//        Allows all users to access the /signup page, as well as the css and js files.
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
//        Allows authenticated users to make any request that's not explicitly covered elsewhere.
                .anyRequest().authenticated();

        http.formLogin()
//        Generates a login form at /login and allows anyone to access it.
                .loginPage("/login")
                .permitAll();

        http.formLogin()
//        Redirects successful logins to the /home page.
                .defaultSuccessUrl("/home", true);
//        .defaultSuccessUrl("/tacos", true);

        http.logout().logoutSuccessUrl("/login");
    }
}
