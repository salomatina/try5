package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.people.Person;

import java.util.Objects;

public class Request {
    private final Book book;
    private final Person person;

    public Request(Book book, Person person) {
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

    public Person getPerson() {
        return person;
    }
}
