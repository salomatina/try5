package ru.mephi.books;

import ru.mephi.Request;

import java.util.LinkedList;
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
}
