package com.olegstashkiv.booksstore;

import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooksStoreApplication {
    private final BookService bookService;

    @Autowired
    public BooksStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BooksStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book lordOfTheMysteries = new Book();
            lordOfTheMysteries.setTitle("Lord Of The Mysteries");
            lordOfTheMysteries.setAuthor("Cuttlefish That Loves Diving");
            lordOfTheMysteries.setIsbn("589649");
            lordOfTheMysteries.setPrice(BigDecimal.valueOf(950));
            lordOfTheMysteries.setDescription("In the waves of steam and machinery,"
                    + "who could achieve extraordinary? "
                    + "In the fogs of history and darkness, who was whispering?"
                    + " I woke up from the realm of mysteries and opened my eyes to the world.");
            lordOfTheMysteries.setCoverImage("https://static.wikia.nocookie.net/"
                    + "lord-of-the-mystery/images/1/11/"
                    + "8435e5dde71190ef76c64df449578a16fdfaaf51cac1.jpg"
                    + "/revision/latest?cb=20210501120004");

            Book cobzar = new Book();
            cobzar.setTitle("Kobzar");
            cobzar.setAuthor("Taras Shevchenko");
            cobzar.setIsbn("781452");
            cobzar.setPrice(BigDecimal.valueOf(500));

            System.out.println(bookService.save(lordOfTheMysteries));
            System.out.println(bookService.save(cobzar));

            bookService.findAll().forEach(System.out::println);
        };
    }
}
