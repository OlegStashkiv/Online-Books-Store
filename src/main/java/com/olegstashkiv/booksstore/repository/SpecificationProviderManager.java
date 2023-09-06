package com.olegstashkiv.booksstore.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationParameter(String key);
}
