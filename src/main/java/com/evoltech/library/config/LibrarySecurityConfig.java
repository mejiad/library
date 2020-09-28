package com.evoltech.library.config;

import com.evoltech.library.repository.UsuarioRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {
    private UsuarioRepository personRepository;

    public LibrarySecurityConfig(UsuarioRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new DirectoryUserDetailsService(this.personRepository));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .headers()
                .disable()
                 // .addHeaderWriter(new StaticHeadersWriter("X-Frame-Options","'SAMEORIGIN'"))
        .authorizeRequests()
                // .antMatchers("/pdf/pdf0*").permitAll()
                // .antMatchers("/home").permitAll()
                .antMatchers("/colecciones/*").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/toDos*").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
}
