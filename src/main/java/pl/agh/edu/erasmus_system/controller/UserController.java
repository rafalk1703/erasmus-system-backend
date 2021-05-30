package pl.agh.edu.erasmus_system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.erasmus_system.model.User;
import pl.agh.edu.erasmus_system.repository.UserRepository;
import pl.agh.edu.erasmus_system.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/users/")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public List<User> getUser() {
        return userService.getAllUsers();
    }
}
