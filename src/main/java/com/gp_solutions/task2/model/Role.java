package com.gp_solutions.task2.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER"),
    HR("HR");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
