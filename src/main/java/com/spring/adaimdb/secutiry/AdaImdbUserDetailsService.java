package com.spring.adaimdb.secutiry;


import com.spring.adaimdb.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.spring.adaimdb.models.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Component
public class AdaImdbUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public AdaImdbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        User currentUser = user.get();

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(currentUser.getUsername());
        builder.password(currentUser.getPassword());

        return builder.build();
    }
}
