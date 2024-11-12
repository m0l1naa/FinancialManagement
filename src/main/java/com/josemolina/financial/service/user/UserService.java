package com.josemolina.financial.service.user;

import com.josemolina.financial.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(String id);

    User save(User user);

    User update(String id, User user);

    void deleteById(String id);

}