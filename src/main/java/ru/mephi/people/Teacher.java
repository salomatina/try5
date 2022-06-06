package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.Record;
import ru.mephi.Request;
import ru.mephi.books.*;

public class Teacher extends Person {

    private final String patronymic;

    public Teacher(String name, String surname, String patronymic, int skl) {
        super(name, surname, skl);
        this.patronymic = patronymic;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public void takeBook(Book book, Library library) throws BookException {
        library.giveBook(book, this);
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " + getPatronymic();
    }
}
