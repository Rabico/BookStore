package book.store.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email is required")
        String email,
        @NotBlank(message = "Password must not be blank")
        @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password
) {
}
