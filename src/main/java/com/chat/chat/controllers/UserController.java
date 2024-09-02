package com.chat.chat.controllers;

import com.chat.chat.config.TokenService;
import com.chat.chat.model.AuthRequestDTO;
import com.chat.chat.model.AuthResponseDTO;
import com.chat.chat.model.User;
import com.chat.chat.repository.UserRepository;
import com.chat.chat.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServicesImpl userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    private ResponseEntity<?> signUp(@RequestBody User user) {
        User user1 = userServices.signUpUser(user);
        String token = tokenService.generateToken(user1);
        return ResponseEntity.ok(new AuthResponseDTO(user1.getId(), token, tokenService.getExpirationDate()));
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO){
            User user = this.userRepository.findByEmail(authRequestDTO.email());
            if(passwordEncoder.matches(authRequestDTO.password(), user.getPassword())){
                String token = this.tokenService.generateToken(user);
                return ResponseEntity.ok(new AuthResponseDTO(user.getId(), token, tokenService.getExpirationDate()) );
            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect credentials. Try again!");
    }

    @GetMapping
    private ResponseEntity<?> getUsers(){
        List<User> userList = userServices.listAllUsers();
        if(userList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found!");
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUsersById(@RequestParam Long id){
        User user = userServices.listUserById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUsersById(@PathVariable Long id){
        userServices.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<?> updateUser(@RequestBody User user){
        User user1 = userServices.editUser(user);
        return ResponseEntity.ok(user1);
    }
}
