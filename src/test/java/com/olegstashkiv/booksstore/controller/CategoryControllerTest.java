package com.olegstashkiv.booksstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olegstashkiv.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.olegstashkiv.booksstore.dto.category.CategoryDto;
import com.olegstashkiv.booksstore.dto.category.CreateCategoryRequestDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private CreateCategoryRequestDto categoryRequestDto;
    private CategoryDto categoryDto;
    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;
    private BookDtoWithoutCategoryIds bookDto1;

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
        categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName("Test Category");
        categoryRequestDto.setDescription("Test Category Description");

        categoryDto = new CategoryDto();
        categoryDto.setId(21L);
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Category Description");

        categoryDto1 = new CategoryDto();
        categoryDto1.setId(10L);
        categoryDto1.setName("Test Category First");
        categoryDto1.setDescription("Test Description for Category First");

        categoryDto2 = new CategoryDto();
        categoryDto2.setId(20L);
        categoryDto2.setName("Test Category Second");
        categoryDto2.setDescription("Test Description for Category Second");

        bookDto1 = new BookDtoWithoutCategoryIds();
        bookDto1.setId(1L);
        bookDto1.setAuthor("Test Author First");
        bookDto1.setTitle("Test Title First");
        bookDto1.setIsbn("978-0262033848");
        bookDto1.setPrice(BigDecimal.valueOf(500));
        bookDto1.setDescription("Test Description for Book 1");
        bookDto1.setCoverImage("http://example.com/test.jpg");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Test create a new category endpoint")
    public void addCategory_ValidRequest_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);
        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class
        );
        EqualsBuilder.reflectionEquals(categoryRequestDto, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Test find all categories endpoint")
    public void getAllCategories_ValidRequest_Success() throws Exception {
        List<CategoryDto> expected = List.of(categoryDto, categoryDto1, categoryDto2);

        MvcResult result = mockMvc.perform(
                        get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CategoryDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), CategoryDto[].class
        );
        assertEquals(3, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Test find category by id endpoint")
    public void findCategoryById_ValidId_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/categories/10")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class
        );
        assertEquals(categoryDto1, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Test update category by id endpoint")
    public void updateCategoryById_ValidId_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);
        MvcResult result = mockMvc.perform(
                        put("/api/categories/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(categoryRequestDto, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Test get books by category id endpoint")
    public void getBooksByCategoryId_ValidRequest_Success() throws Exception {
        List<BookDtoWithoutCategoryIds> expected = List.of(bookDto1);

        MvcResult result = mockMvc.perform(
                        get("/api/categories/10/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDtoWithoutCategoryIds[].class
        );
        assertEquals(1, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }
}
