package com.josemolina.financial.controller;

import com.josemolina.financial.model.User;
import com.josemolina.financial.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService usuarioService;

    @GetMapping
    public List<User> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{idUser}")
    public User findById(@PathVariable String idUser) {
        return usuarioService.findById(idUser);
    }

    @PostMapping()
    public User save(@RequestBody User user) {
        return usuarioService.save(user);
    }

    @PutMapping("/{idUser}")
    public User update(@PathVariable String idUser, @RequestBody User user) {
        return usuarioService.update(idUser, user);
    }

    @DeleteMapping("/{idUser}")
    public void deleteById(@PathVariable String idUser) {
        usuarioService.deleteById(idUser);
    }

}