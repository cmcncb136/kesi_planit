package com.kesi.planit.core.content.permission;

import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;

import java.util.Optional;

public class ContentPermissionChecker {
    public static boolean can(ContentPermission permission, Optional<User> currentUser, User onwerUser) {
        return permission == ContentPermission.PUBLIC;
    }

    public static boolean can(ContentPermission permission, User currentUser, User onwerUser) {
        switch (permission) {
            case PUBLIC -> {
                return true;
            }
            case OWNER_ONLY -> {
                return currentUser.equals(onwerUser) || onwerUser.getRole().isAtLeast(Role.MANAGER);
            }
            case MANAGER_ONLY -> {
                return onwerUser.getRole().isAtLeast(Role.MANAGER);
            }

        }
        throw new IllegalArgumentException("permission not supported");
    }
}
