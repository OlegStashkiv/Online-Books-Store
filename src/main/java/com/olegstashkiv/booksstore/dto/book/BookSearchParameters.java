package com.olegstashkiv.booksstore.dto.book;

public record BookSearchParameters(String title, String[] author, String[] isbn) {
}
