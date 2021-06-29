package com.nnk.springboot.configuration.security;


import com.nnk.springboot.configuration.handler.CustomSuccessHandler;
import com.nnk.springboot.services.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()

                //Pages access configuration
                .antMatchers("/user/**").hasAuthority("ADMIN")
                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**"
                        , "/trade/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/", "/login", "/logout", "/403"
                        , "/register", "/PMBFondGris.PNG", "/PMBFondBlanc.PNG").permitAll()
                .anyRequest().authenticated()
                .and()

                //login configuration
                .formLogin()
                .successHandler(successHandler())

                //logout configuration
                .and().logout()
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/")

                //Exception Handling
                .and().exceptionHandling().accessDeniedPage("/app/error")

        ;
    }

    @Bean
    public CustomSuccessHandler successHandler(){
        return  new CustomSuccessHandler();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
