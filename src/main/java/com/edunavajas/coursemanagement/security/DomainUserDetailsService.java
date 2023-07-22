package com.edunavajas.coursemanagement.security;

import com.edunavajas.coursemanagement.jpa.repository.UserJpaRepository;
import com.edunavajas.coursemanagement.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);

        return userJpaRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username)
            .map(user -> createSpringSecurityUser(username, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(),
            grantedAuthorities);
    }
}
