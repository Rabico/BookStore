package book.store.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}
