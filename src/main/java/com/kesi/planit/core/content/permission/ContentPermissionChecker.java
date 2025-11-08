package com.kesi.planit.core.content.permission;

import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;

import java.util.Optional;

public class ContentPermissionChecker {
    public static boolean can(ContentPermission permission, Optional<User> currentUser, User onwerUser) {
        if(currentUser.isEmpty())
            return permission == ContentPermission.PUBLIC;

        return can(permission, currentUser.get(), onwerUser);
    }

    public static boolean can(ContentPermission permission, User currentUser, User onwerUser) {
        switch (permission) {
            case PUBLIC -> {
                return true;
            }
            case OWNER_ONLY -> {
                return currentUser.equals(onwerUser) || currentUser.getRole().isAtLeast(Role.MANAGER);
            }
            case MANAGER_ONLY -> {
                return currentUser.getRole().isAtLeast(Role.MANAGER);
            }

        }
        throw new IllegalArgumentException("permission not supported");
    }
}
