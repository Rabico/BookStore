package book.store.controller;

import book.store.dto.user.UserRegistrationRequestDto;
import book.store.dto.user.UserResponseDto;
import book.store.exception.RegistrationException;
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

    @PostMapping("/registration")
    @Operation(summary = "Register a new user",
            description = "Confirms the provided data, registers a new user,"
                    + " and returns the created user.")
    public UserResponseDto registration(@RequestBody @Valid
                                            UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {

        return userService.register(userRegistrationRequestDto);
    }
}
