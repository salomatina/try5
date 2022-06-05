package ru.mephi.people;

import ru.mephi.books.*;

public class Student extends People {

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
    public void takeBook(Book book) throws BookException {
        if (book.getLanguage().equals(Language.ENGLISH)) {
            if (book instanceof Textbook) {
                if (((Textbook) book).getLevel() != getDegree()) {
                    return;
                }
            }
            else {
                if (((Fiction) book).getSkills() != getSkills()) {
                    return;
                }
            }
        }
        getBookList().add(book.toString());
    }
}
