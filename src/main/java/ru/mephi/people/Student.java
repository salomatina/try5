package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.books.*;

public class Student extends Person {

    private final Degree degree;

    public Student(int dgr, int skl, String name, String surname) {
        super(name, surname, skl);
        if (dgr == 0) {
            degree = Degree.BACHELORS;
        }
        else {
            degree = Degree.MASTERS;
        }
    }

    public Degree getDegree() {
        return degree;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    @Override
    public void takeBook(Book book, Library library) throws BookException {
        library.giveBook(book, this);
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
