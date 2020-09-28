package com.evoltech.library.config;

import com.evoltech.library.model.jpa.Usuario;
import com.evoltech.library.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DirectoryUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(DirectoryUserDetailsService.class);
    private final UsuarioRepository repo;

    public DirectoryUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final Usuario person = this.repo.findByEmailIgnoreCase(username);

            if (person != null) {
                PasswordEncoder encoder = PasswordEncoderFactories.
                        createDelegatingPasswordEncoder();
                // String password = person.getPassword();
                // String password = encoder.encode(person.getPassword());
                String password = person.getPassword();
                log.warn("PS:" + person.getPassword());
                log.warn("cr:" + password);

                return User.withUsername(person.getEmail()).
                        accountLocked(!person.isEnabled()).password(password).
                        roles(person.getRole()).build();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
