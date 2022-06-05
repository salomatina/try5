package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.Record;
import ru.mephi.Request;
import ru.mephi.books.*;

public class Teacher extends People {

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
        if (book instanceof Fiction) {
            if (book.getLanguage() == Language.ENGLISH) {
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
                book.getBookRequests().add(request);
                library.getRequestLine().put(book, book.getBookRequests());
        }
    }
}
