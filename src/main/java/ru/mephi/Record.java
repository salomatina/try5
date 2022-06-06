package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.people.Person;

public class Record {
    private int length;
    private final Book book;
    private final Person person;

    public Record(Book book, Person person) {
        length = 3;
        this.book = book;
        this.person = person;
    }

    public void makeLengthShorter() {
        length--;
    }

    public int getLength() {
        return length;
    }

    public Book getBook() {
        return book;
    }

    public Person getPerson() {
        return person;
    }
}
