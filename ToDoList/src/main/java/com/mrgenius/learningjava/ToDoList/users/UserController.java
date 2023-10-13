package com.mrgenius.learningjava.ToDoList.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUsersReoository userRepo;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel user){
        var users = userRepo.findByUsername(user.getUsername());
        if(users != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
        var cryptoPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(cryptoPassword);
        var userCreated = this.userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
