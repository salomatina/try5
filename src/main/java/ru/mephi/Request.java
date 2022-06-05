package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.people.People;

public class Request {
    private final Book book;
    private final People person;

    public Request(Book book, People person) {
        this.book = book;
        this.person = person;
    }

    public Book getBook() {
        return book;
    }

    public People getPerson() {
        return person;
    }
}
