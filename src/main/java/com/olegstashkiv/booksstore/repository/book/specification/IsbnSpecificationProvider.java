package com.olegstashkiv.booksstore.repository.book.specification;

import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY_FIELD = "isbn";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get(KEY_FIELD)
                .in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKey() {
        return KEY_FIELD;
    }
}
