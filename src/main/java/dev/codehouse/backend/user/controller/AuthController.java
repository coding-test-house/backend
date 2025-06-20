package dev.codehouse.backend.user.controller;


import dev.codehouse.backend.user.dto.UserRequestDto;
import dev.codehouse.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody UserRequestDto request) {
        userService.userExists(request.getUsername());
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.login(request).toString());
    }
}
