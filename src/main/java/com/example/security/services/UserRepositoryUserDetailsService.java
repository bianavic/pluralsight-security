package com.example.security.services;

import com.example.security.models.User;
import com.example.security.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static class UserUserDetails implements UserDetails {

        private final User user;
        private final Set<GrantedAuthority> authorities;

        public UserUserDetails(User user) {
            this.user = user;
            this.authorities = this.user.getAuthorities()
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()))
                    .collect(Collectors.toSet());
        }

        public User getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        @Override
        public String getPassword() {
            return this.user.getPassword();
        }

        @Override
        public String getUsername() {
            return this.user.getCpf();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByCpf(username);
        if (null != user) {
            return new UserUserDetails(user);
        } else throw new UsernameNotFoundException("couldn't find " + username + "!");
    }
}
