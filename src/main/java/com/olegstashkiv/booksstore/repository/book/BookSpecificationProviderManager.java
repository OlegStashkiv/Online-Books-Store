package com.olegstashkiv.booksstore.repository.book;

import com.olegstashkiv.booksstore.exception.NoSuchElementException;
import com.olegstashkiv.booksstore.model.Book;
import com.olegstashkiv.booksstore.repository.SpecificationProvider;
import com.olegstashkiv.booksstore.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationParameter(String key) {
        return bookSpecificationProviders.stream()
                .filter(prov -> prov.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Can't find a specification provider for key " + key)
                );
    }
}
