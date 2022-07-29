package com.dakual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600, origins = "*")
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getUserById (@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.ok("Not found!");
    }
}
