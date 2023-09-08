package com.olegstashkiv.booksstore.dto;

public record BookSearchParameters(String title, String[] author, String[] isbn) {
}
