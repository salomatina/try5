package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.Request;
import ru.mephi.books.Book;
import ru.mephi.books.BookException;

import java.util.HashSet;
import java.util.Set;

public abstract class People {

    private Set<Book> bookList;
    private Skills skills;
    protected String name;
    protected String surname;

    public People(String name, String surname, int skl) {
        this.name = name;
        this.surname = surname;
        if (skl == 1) {
            skills = Skills.A1;
        }
        else if (skl == 2) {
            skills = Skills.A2;
        }
        else if (skl == 3) {
            skills = Skills.B1;
        }
        else if (skl == 4) {
            skills = Skills.B2;
        }
        else if (skl == 5) {
            skills = Skills.C1;
        }
        else {
            skills = Skills.C2;
        }
    }

    public void takeBook(Book book, Library library) throws BookException {
        getBookList();
        Request request = new Request(book, this);
        if (library.getAvailableBooks().get(book) > 0) {
            bookList.add(book);
        }
        else {
            book.getBookRequests().add(request);
            library.getRequestLine().put(book, book.getBookRequests());
        }
    }

    public void returnBook(Book book, Library library) throws BookException {
        bookList.remove(book);
        try {
            Request request = (Request) library.getRequestLine().get(book).poll();
            if (request != null) {
                request.getPerson().takeBook(book, library);
            }
            else {
                library.getAvailableBooks().put(book, library.getAvailableBooks().get(book) + 1);
            }
        }
        catch (NullPointerException e) {
            library.getAvailableBooks().put(book, library.getAvailableBooks().get(book) + 1);
        }
    }

    public Set<Book> getBookList() {
        if (bookList == null) {
            bookList = new HashSet<>();
        }
        return bookList;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
