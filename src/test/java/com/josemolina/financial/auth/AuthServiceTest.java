package com.josemolina.financial.auth;

import com.josemolina.financial.model.User;
import com.josemolina.financial.repository.UserRepository;
import com.josemolina.financial.security.JwtTokenUtil;
import com.josemolina.financial.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthService authService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhone("123456789");
    }

    @Test
    void login_ValidCredentials_ShouldReturnJwtToken() {
        // Arrange
        String email = "john.doe@example.com";
        String password = "password123";
        String expectedToken = "mocked-jwt-token";

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtTokenUtil.generateToken(user.getId())).thenReturn(expectedToken);

        // Act
        String actualToken = authService.login(email, password);

        // Assert
        assertNotNull(actualToken);
        assertEquals(expectedToken, actualToken);
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtTokenUtil, times(1)).generateToken(user.getId());
    }

    @Test
    void login_InvalidCredentials_ShouldThrowException() {
        // Arrange
        String email = "john.doe@example.com";
        String wrongPassword = "wrongPassword";

        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(email, wrongPassword);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtTokenUtil, never()).generateToken(anyString());
    }

    @Test
    void login_NonExistentUser_ShouldThrowException() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(email, password);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtTokenUtil, never()).generateToken(anyString());
    }

}