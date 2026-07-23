package book.store.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Tittle must not be blank")
    @Size(max = 30, message = "Tittle must not exceed 30 characters")
    private String title;
    @NotBlank(message = "Tittle must not be blank")
    @Size(max = 30, message = "Author must not exceed 30 characters")
    private String author;
    @NotNull(message = "ISBN is required")
    @ISBN(message = "ISBN must have ISBN pattern")
    private String isbn;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be greater than zero")
    private BigDecimal price;
    @Size(max = 100, message = "Author must not exceed 100 characters")
    private String description;
    @URL(protocol = "https", message = "URL must by HTTPS protocol")
    private String coverImage;
}


