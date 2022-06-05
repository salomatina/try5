package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.people.People;

public class Record {
    private int length;
    private Book book;
    private People person;

    public Record(Book book, People person) {
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

    public People getPerson() {
        return person;
    }
}
