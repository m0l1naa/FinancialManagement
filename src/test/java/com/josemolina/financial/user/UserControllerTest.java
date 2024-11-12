package com.josemolina.financial.user;

import com.josemolina.financial.controller.UserController;
import com.josemolina.financial.model.User;
import com.josemolina.financial.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void testFindAll() {
        List<User> users = Arrays.asList(user);
        when(userService.findAll()).thenReturn(users);

        List<User> result = userController.findAll();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(userService.findById("1")).thenReturn(user);

        User result = userController.findById("1");

        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(userService.save(any(User.class))).thenReturn(user);

        User result = userController.save(user);

        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {
        when(userService.update(eq("1"), any(User.class))).thenReturn(user);

        User result = userController.update("1", user);

        assertEquals("John Doe", result.getName());
        verify(userService, times(1)).update(eq("1"), any(User.class));
    }

    @Test
    void testDeleteById() {
        userController.deleteById("1");

        verify(userService, times(1)).deleteById("1");
    }

}