package com.olegstashkiv.booksstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olegstashkiv.booksstore.dto.book.BookDto;
import com.olegstashkiv.booksstore.dto.book.CreateBookRequestDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    private CreateBookRequestDto createBookRequest;
    private CreateBookRequestDto updateBookRequest;
    private BookDto bookDto1;
    private BookDto bookDto2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/book/add-books-categories-to-table.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/book/remove-books-categories-from-table.sql")
            );
        }
    }

    @BeforeEach
    void setUp() {
        createBookRequest = new CreateBookRequestDto();
        createBookRequest.setAuthor("Test Author");
        createBookRequest.setTitle("Test Title");
        createBookRequest.setIsbn("978-1617292231");
        createBookRequest.setPrice(BigDecimal.valueOf(500));
        createBookRequest.setDescription("Test Description for Book 1");
        createBookRequest.setCoverImage("http://example.com/test.jpg");
        createBookRequest.setCategoryIds(Set.of(10L));

        bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setAuthor("Test Author First");
        bookDto1.setTitle("Test Title First");
        bookDto1.setIsbn("978-0262033848");
        bookDto1.setPrice(BigDecimal.valueOf(500));
        bookDto1.setDescription("Test Description for Book 1");
        bookDto1.setCoverImage("http://example.com/test.jpg");
        bookDto1.setCategories(Set.of(10L));

        bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setAuthor("Test Author Second");
        bookDto2.setTitle("Test Title Second");
        bookDto2.setIsbn("978-0-9767736-6-5");
        bookDto2.setPrice(BigDecimal.valueOf(300));
        bookDto2.setDescription("Test Description for Book 2");
        bookDto2.setCoverImage("http://example.com/test.jpg");
        bookDto2.setCategories(Set.of(20L));

        updateBookRequest = new CreateBookRequestDto();
        updateBookRequest.setAuthor("Test Author Second");
        updateBookRequest.setTitle("Test Title Second");
        updateBookRequest.setIsbn("978-3-16-148410-0");
        updateBookRequest.setPrice(BigDecimal.valueOf(300));
        updateBookRequest.setDescription("Test Description for Book 2");
        updateBookRequest.setCoverImage("http://example.com/test.jpg");
        updateBookRequest.setCategoryIds(Set.of(20L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test create a new book endpoint")
    void createBook_ValidRequestDto_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(createBookRequest);
        MvcResult result = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class
        );

        EqualsBuilder.reflectionEquals(createBookRequest, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Test find all books endpoint")
    public void getAllBook_ValidRequest_Success() throws Exception {
        List<BookDto> expected = List.of(bookDto1, bookDto2);

        MvcResult result = mockMvc.perform(
                        get("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class
        );
        assertEquals(2, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Test find book by id endpoint")
    public void findById_ValidId_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class
        );
        assertEquals(bookDto1, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Test update book by id endpoint")
    public void updateBookById_ValidId_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(updateBookRequest);
        MvcResult result = mockMvc.perform(
                        put("/api/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class
        );

        EqualsBuilder.reflectionEquals(updateBookRequest, actual, "id");
    }
}
