package ru.mephi.books;

import ru.mephi.Request;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public abstract class Book {

    protected Language language;
    protected String name;
    protected String author;
    protected Queue<Request> bookRequests;


    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        bookRequests = new LinkedList<>();
    }

    public Language getLanguage() {
        return language;
    }

    public Queue<Request> getBookRequests() {
        return bookRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return language == book.language && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, name, author);
    }
}
