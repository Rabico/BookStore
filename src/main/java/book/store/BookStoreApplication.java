package book.store;

import book.store.model.Book;
import book.store.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book firstBook = new Book();
            firstBook.setAuthor("First Author");
            firstBook.setTitle("First Book");
            firstBook.setIsbn("12345");
            firstBook.setPrice(BigDecimal.TEN);

            bookService.save(firstBook);
            System.out.println(bookService.findAll());
        };
    }
}
