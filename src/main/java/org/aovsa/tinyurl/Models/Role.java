package org.aovsa.tinyurl.Models;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");
    private final String role;

    Role(String value) {
        this.role = value;
    }
}
