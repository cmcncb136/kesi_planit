package com.kesi.planit.core;

import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
@Slf4j
public class RoleCheckAspect {

    @Around(value = "@annotation(roleCheck) && args(user, ..)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleCheck roleCheck, User user) throws Throwable {
        Role requriedRole = roleCheck.allowLevel();
        log.info("Checking role {} for user {} user role {}", requriedRole, user, user.getRole());

        if(requriedRole.getLevel() < user.getRole().getLevel()) {
            throw new AccessDeniedException("잘못된 요청(권한 부족)");
        }

        return joinPoint.proceed();
    }
}



