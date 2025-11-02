package com.skydiveforecast.infrastructure.aop;

import com.skydiveforecast.infrastructure.security.PermissionSecurityService;
import com.skydiveforecast.infrastructure.security.annotation.PermissionSecurity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionSecurityAspectTest {

    @Mock
    private PermissionSecurityService permissionSecurityService;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private PermissionSecurityAspect permissionSecurityAspect;

    @Test
    void checkPermission_ShouldProceedWhenUserHasPermission() throws Throwable {
        // Arrange
        String permission = "READ";
        Method method = TestClass.class.getMethod("testMethod");
        Object expectedResult = "success";
        
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(permissionSecurityService.hasPermission(permission)).thenReturn(true);
        when(joinPoint.proceed()).thenReturn(expectedResult);

        // Act
        Object result = permissionSecurityAspect.checkPermission(joinPoint);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
        verify(permissionSecurityService).hasPermission(permission);
        verify(joinPoint).proceed();
    }

    @Test
    void checkPermission_ShouldThrowAccessDeniedWhenUserLacksPermission() throws Throwable {
        // Arrange
        String permission = "WRITE";
        Method method = TestClass.class.getMethod("testMethodWrite");
        
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(method);
        when(permissionSecurityService.hasPermission(permission)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> permissionSecurityAspect.checkPermission(joinPoint))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("Access Denied");
        
        verify(joinPoint, never()).proceed();
    }

    static class TestClass {
        @PermissionSecurity(permission = "READ")
        public void testMethod() {
        }

        @PermissionSecurity(permission = "WRITE")
        public void testMethodWrite() {
        }
    }
}
