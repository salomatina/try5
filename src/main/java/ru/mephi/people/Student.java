package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.Record;
import ru.mephi.Request;
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
    public void takeBook(Book book, Library library) throws BookException {
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
        int numberOfBooks = library.getAvailableBooks().get(book);
        if (numberOfBooks > 0) {
            int sizeBefore = getBookList().size();
            getBookList().add(book);
            if (getBookList().size() > sizeBefore) {
                library.getAvailableBooks().put(book, numberOfBooks - 1);
                Record record = new Record(book, this);
                library.getTakenBooks().add(record);
            }
        }
        else {
            Request request = new Request(book, this);
            if (!book.getBookRequests().contains(request)) {
                book.getBookRequests().add(request);
//                library.getRequestLine().put(book, book.getBookRequests());
                System.out.println("request " + this.toString() + " " + book);
            }
            else {
                System.out.println("request exists");
            }
        }
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
