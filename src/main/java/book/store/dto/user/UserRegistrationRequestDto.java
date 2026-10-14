package book.store.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is required")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    @NotBlank(message = "Password must not be blank")
    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String repeatPassword;
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    private String shippingAddress;
}
