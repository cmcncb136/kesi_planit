package com.kesi.planit.core.role;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(1), MANAGER(2), USER(3);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public boolean isAtLeast(Role role) {
        return level <= role.level;
    }
}
