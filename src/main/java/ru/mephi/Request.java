package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.people.People;

import java.util.Objects;

public class Request {
    private final Book book;
    private final People person;

    public Request(Book book, People person) {
        this.book = book;
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(book, request.book) && Objects.equals(person, request.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, person);
    }

    public Book getBook() {
        return book;
    }

    public People getPerson() {
        return person;
    }
}
