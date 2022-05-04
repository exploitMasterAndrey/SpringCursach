package main.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER, ADMIN;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return Set.of(name()).stream().map(s -> new SimpleGrantedAuthority(name())).collect(Collectors.toSet());
    }
}
