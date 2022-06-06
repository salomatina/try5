package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.books.BookException;
import ru.mephi.people.Person;

public class Engine {

    public void randomMove(Library library) throws BookException {
        Randomizer randomizer = new Randomizer();
        Person person = library.getRandomPerson();
        Book book = person.chooseBook(library);
        int sizeBefore = person.getBookList().size();
        person.takeBook(book, library);
        while (person.getBookList().size() == sizeBefore) {
            int n = randomizer.getBinaryNumber();
            if (n == 0) {
                person = library.getRandomPerson();
                sizeBefore = person.getBookList().size();
            }
            else {
                book = library.getRandomBook();
            }
            person.takeBook(book, library);
        }
    }

    public void returnAllBooks(Library library) {
        for (int i = 0; i < library.getTakenBooks().size(); i++) {
            Record record = library.getTakenBooks().get(i);
            record.makeLengthShorter();
            if (record.getLength() == 0) {
                Person person = record.getPerson();
                Book book = record.getBook();
                try {
                    person.returnBook(book, library);
                }
                catch (BookException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void takeAllBooks(Library library) {
        int n = (library.getStudents().size() + library.getTeachers().size()) / 4;
        for (int i = 0; i < n; i++) {
            try {
                randomMove(library);
            }
            catch (BookException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeAllMoves(Library library) {
        returnAllBooks(library);
        takeAllBooks(library);
    }

}
