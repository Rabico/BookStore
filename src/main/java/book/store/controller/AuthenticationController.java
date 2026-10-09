package book.store.controller;

import book.store.dto.user.UserLoginRequestDto;
import book.store.dto.user.UserLoginResponseDto;
import book.store.dto.user.UserRegistrationRequestDto;
import book.store.dto.user.UserResponseDto;
import book.store.exception.RegistrationException;
import book.store.security.AuthenticationService;
import book.store.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User authentication", description = "Endpoint for users authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user",
            description = "Authenticates a user using "
                    + " the provided credentials and returns a JWT.")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/registration")
    @Operation(summary = "Register a new user",
            description = "Confirms the provided data, registers a new user,"
                    + " and returns the created user.")
    public UserResponseDto register(@RequestBody @Valid
                                            UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {

        return userService.register(userRegistrationRequestDto);
    }
}
