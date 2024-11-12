package com.josemolina.financial.auth;

import com.josemolina.financial.controller.AuthController;
import com.josemolina.financial.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private Map<String, String> credentials;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password123");
    }

    @Test
    void testLogin_Success() {
        String token = "mockToken";
        when(authService.login("test@example.com", "password123")).thenReturn(token);

        String result = authController.login(credentials);

        assertEquals(token, result);
        verify(authService, times(1)).login("test@example.com", "password123");
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(authService.login("test@example.com", "wrongPassword")).thenThrow(new RuntimeException("Invalid credentials"));
        credentials.put("password", "wrongPassword");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(credentials);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authService, times(1)).login("test@example.com", "wrongPassword");
    }

}